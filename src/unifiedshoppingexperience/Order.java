package unifiedshoppingexperience;

import shared.Address;
import interfaces.CallBack;
import interfaces.OrderDTO;
import interfaces.ProductLineDTO;
import java.math.BigDecimal;
import java.util.Date;
import persistence.DataStore;
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
     * Creates an order at runtime with a cart and a price.
     *
     * @param cart
     * @param price The total cost of the order.
     * @param c
     */
    public Order(Cart cart, BigDecimal price, Customer c)
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

        DataStore.getPersistence().persist(this, c);
    }

    /**
     * Creates an order at startup, this object is already persisted by teh
     * database.
     *
     * @param id
     * @param price
     * @param status
     * @param paymentMethod
     * @param purchaseDate
     * @param dispatchedDate
     * @param cart
     * @param deliveryAddress
     */
    public Order(Integer id, BigDecimal price, OrderStatus status, String paymentMethod, Date purchaseDate, Date dispatchedDate, Cart cart, Address deliveryAddress)
    {
        this.id = id;
        this.price = price;
        this.status = status;
        this.paymentMethod = paymentMethod;
        this.purchaseDate = purchaseDate;
        this.dispatchedDate = dispatchedDate;
        this.cart = cart;
        this.deliveryAddress = deliveryAddress;

        incrementOrderCreations();
    }

    /**
     * Increments the amount of orders created. This is used to provide unique
     * ID for orders created.
     */
    private static void incrementOrderCreations()
    {
        ++orderCreations;
    }

    /**
     * Sets the payment method of the order.
     *
     * @param paymentMethod The payment method as a string. Ie "paypal", "visa"
     * or "pick-up-point".
     */
    public void setPaymentMethod(String paymentMethod)
    {
        this.paymentMethod = paymentMethod;
    }

    /**
     * Sets the date the order was dispatched to the current date.
     */
    public void setDispatchedDate()
    {
        dispatchedDate = new Date();
    }

    /**
     * Sets the date the order was dispatched to a specified date.
     *
     * @param date The specified date the order was dispatched
     */
    public void setDispatchedDate(Date date)
    {
        dispatchedDate = date;
    }

    /**
     * Sets the address the order is to be delivered to.
     *
     * @param address The address the order is to be delivered to.
     */
    public void setAddress(Address address)
    {
        this.deliveryAddress = address;
    }

    @Override
    public ProductLineDTO[] getProductLines()
    {
        return cart.getProducts().toArray(new ProductLineDTO[0]);
    }

    /**
     * Provides a callback that sets the order to paid if called, and calls
     * another specified callback.
     *
     * @param eventTrigger The specified callback to call when order is paid
     * for.
     * @return Returns a callback that sets the order to paid if called.
     */
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

    public int getCartID()
    {
        return cart.getID();
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
    public Address getAddress()
    {
        return deliveryAddress;
    }

    @Override
    public OrderStatus getStatus()
    {
        return status;
    }
}
