package unifiedshoppingexperience;

import shared.Address;
import interfaces.CallBack;
import interfaces.OrderDTO;
import interfaces.ProductLineDTO;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import persistence.DatabaseConnection;
import shared.OrderStatus;

/**
 * Keeps information about an order.
 *
 * @author Gruppe12
 */
public class Order implements OrderDTO
{
    private static int orderCreations;
    private final int id;
    private Date purchaseDate;
    private Date dispatchedDate;
    private final BigDecimal price;
    private final Cart cart;
    private String paymentMethod;
    private Address deliveryAddress;
    private OrderStatus status;

    static
    {
        orderCreations = 0;
    }

    /**
     * Creates an order with all product lines, purchase and delivery date and
     * total cost of order.
     *
     * @param cart
     * @param price The total cost of the order.
     */
    public Order(Cart cart, BigDecimal price)
    {
        this.price = price;
        this.id = orderCreations + 1;
        this.cart = cart;
        this.status = OrderStatus.UNPAID;
        this.purchaseDate = null;
        this.paymentMethod = null;
        this.deliveryAddress = null;
        this.dispatchedDate = null;
        incrementOrderCreations();
    }

    public Order(DatabaseConnection db, ResultSet rs) throws SQLException
    {
        this.id = rs.getInt(2);
        this.price = rs.getBigDecimal(3);
        this.status = OrderStatus.values()[rs.getInt(4)];
        this.paymentMethod = rs.getString(5);
        this.purchaseDate = rs.getDate(6);
        this.dispatchedDate = rs.getDate(7);

        ResultSet rs1 = db.Select("SELECT * FROM cart WHERE id = ?;", rs.getInt("cart_id"));
        if (rs1.next())
        {
            this.cart = new ShoppingCart(db, rs);
        }
        else
        {
            this.cart = null;
        }

        ResultSet rs2 = db.Select("SELECT * FROM address WHERE id = ?", rs.getInt("delivery_address"));
        if (rs2.next())
        {
            this.deliveryAddress = new Address(rs2);
        }
        else
        {
            this.deliveryAddress = null;
        }

        incrementOrderCreations();
    }

    public void save(DatabaseConnection db, PreparedStatement orderInsert, PreparedStatement orderUpdate, PreparedStatement cartInsert, PreparedStatement cartUpdate, PreparedStatement addressInsert, PreparedStatement addressUpdate) throws SQLException
    {
        cart.save(db, cartInsert, cartUpdate);
        if (deliveryAddress != null)
        {
            deliveryAddress.save(addressInsert, addressUpdate);
        }

        orderUpdate.setInt(9, id);
        orderUpdate.setBigDecimal(2, price);
        orderUpdate.setInt(3, status.ordinal());
        orderUpdate.setString(4, paymentMethod);

        orderInsert.setInt(2, id);
        orderInsert.setInt(10, id);
        orderInsert.setBigDecimal(3, price);
        orderInsert.setInt(4, status.ordinal());
        orderInsert.setString(5, paymentMethod);

        if (purchaseDate == null)
        {
            orderUpdate.setNull(5, java.sql.Types.DATE);
            orderInsert.setNull(6, java.sql.Types.DATE);
        }
        else
        {
            orderUpdate.setDate(5, new java.sql.Date(purchaseDate.getTime()));
            orderInsert.setDate(6, new java.sql.Date(purchaseDate.getTime()));
        }
        if (dispatchedDate == null)
        {
            orderUpdate.setNull(6, java.sql.Types.DATE);
            orderInsert.setNull(7, java.sql.Types.DATE);
        }
        else
        {
            orderInsert.setDate(6, new java.sql.Date(dispatchedDate.getTime()));
            orderInsert.setDate(7, new java.sql.Date(dispatchedDate.getTime()));
        }

        orderUpdate.setInt(7, cart.getID());
        orderInsert.setInt(8, cart.getID());

        if (deliveryAddress == null)
        {
            orderUpdate.setNull(8, java.sql.Types.INTEGER);
            orderInsert.setNull(9, java.sql.Types.INTEGER);
        }
        else
        {
            orderUpdate.setInt(8, deliveryAddress.getID());
            orderInsert.setInt(9, deliveryAddress.getID());
        }

        orderUpdate.addBatch();
        orderInsert.addBatch();
    }

    private static void incrementOrderCreations()
    {
        ++orderCreations;
    }

    public void setPaymentMethod(String paymentMethod)
    {
        this.paymentMethod = paymentMethod;
    }

    public void setDispatchedDate()
    {
        dispatchedDate = new Date();
    }

    public void setDispatchedDate(Date date)
    {
        dispatchedDate = date;
    }

    public void setAddress(Address address)
    {
        this.deliveryAddress = address;
    }

    @Override
    public ProductLineDTO[] getProductLines() // getOrderLines?
    {
        return cart.getProducts().toArray(new ProductLineDTO[0]);
    }

    public CallBack getCallBack(CallBack eventTrigger)
    {
        CallBack cb = () ->
        {
            purchaseDate = new Date();
            this.status = OrderStatus.PAID;
            eventTrigger.call();
        };
        return cb;
    }

    @Override
    public int getID()
    {
        return id;
    }

    @Override
    public BigDecimal getPrice()
    {
        return price;
    }

    @Override
    public Date getPurchaseDate()
    {
        return purchaseDate;
    }

    @Override
    public Date getDispatchedDate()
    {
        return dispatchedDate;
    }

    @Override
    public int getOrderNumber()
    {
        return id;
    }

    @Override
    public String getPaymentMethod()
    {
        return paymentMethod;
    }

    @Override
    public String getAddress()
    {
        if (deliveryAddress == null)
        {
            return null;
        }
        return deliveryAddress.toString();
    }

    @Override
    public OrderStatus getStatus()
    {
        return status;
    }
}
