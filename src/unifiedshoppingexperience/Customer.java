package unifiedshoppingexperience;

import shared.Address;
import interfaces.CustomerDTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import persistence.DatabaseConnection;
import shared.CreateOrderErrors;
import shared.CreateOrderResult;
import shared.OrderStatus;

/**
 * Contains information about a customer and provides methods to handle the
 * actions the customer should be able to do.
 *
 * @author Gruppe 12
 */
public class Customer implements CustomerDTO
{
    private String ID;
    private String firstName;
    private String surName;
    private String email;
    private String phoneNumber;
    private ShoppingCart shoppingCart;
    private ArrayList<WishList> wishLists;
    private Map<Integer, Order> orders;
    private String personalizedData;
    private Address defaultDeliveryAddress;

    /**
     * Creates a customer with a ID
     *
     * @param ID The ID of the customer within the system.
     */
    public Customer(String ID)
    {
        this.ID = ID;
        this.shoppingCart = new ShoppingCart();
        this.wishLists = new ArrayList();
        this.orders = new HashMap();
    }

    public Customer(DatabaseConnection db, ResultSet rs) throws SQLException
    {
        this.orders = new HashMap();
        this.wishLists = new ArrayList();
        this.ID = rs.getString(1);
        this.firstName = rs.getString(2);
        this.surName = rs.getString(3);
        this.email = rs.getString(4);
        this.phoneNumber = rs.getString(5);
        //this.personalizedData = rs.getString(6);
        ResultSet rs1 = db.Select("SELECT * FROM cart WHERE customer_id = ?;", this.ID);
        ResultSet rs2 = db.Select("SELECT * FROM wishlist NATURAL JOIN cart WHERE customer_id = ?;", this.ID);
        ResultSet rs3 = db.Select("SELECT * FROM \"order\" WHERE customer_id = ?;", this.ID);
        ResultSet rs4 = db.Select("SELECT * FROM address WHERE id = ?;", rs.getInt("address"));

        if (rs1.next())
        {
            this.shoppingCart = new ShoppingCart(db, rs1);
        }
        else
        {
            this.shoppingCart = new ShoppingCart();
        }

        while (rs2.next())
        {
            this.wishLists.add(new WishList(db, rs2));
        }

        while (rs3.next())
        {
            Order order = new Order(db, rs3);
            this.orders.put(order.getID(), order);
        }

        if (rs4.next())
        {
            this.defaultDeliveryAddress = new Address(rs4);
        }
    }

    public void save(DatabaseConnection db, PreparedStatement customerInsert, PreparedStatement customerUpdate) throws SQLException
    {
        customerUpdate.setString(1, firstName);
        customerUpdate.setString(2, surName);
        customerUpdate.setString(3, email);
        customerUpdate.setString(4, phoneNumber);
        customerUpdate.setString(6, ID);

        customerInsert.setString(1, ID);
        customerInsert.setString(7, ID);
        customerInsert.setString(2, firstName);
        customerInsert.setString(3, surName);
        customerInsert.setString(4, email);
        customerInsert.setString(5, phoneNumber);
        if (defaultDeliveryAddress == null)
        {
            customerUpdate.setNull(5, java.sql.Types.INTEGER);
            customerInsert.setNull(6, java.sql.Types.INTEGER);
        }
        else
        {
            customerUpdate.setInt(5, defaultDeliveryAddress.getID());
            customerInsert.setInt(6, defaultDeliveryAddress.getID());
        }
        customerInsert.addBatch();
        customerUpdate.addBatch();

        String addressQuery2 = "UPDATE address SET inhabitant_name = ?, street_address = ?, zip_code = ?, city = ?, country = ? WHERE id = ?;";
        String addressQuery = "INSERT INTO address(id, inhabitant_name, street_address, zip_code, city, country) "
                              + "SELECT ?, ?, ?, ?, ?, ? WHERE NOT EXISTS (SELECT * FROM address WHERE id = ?);";

        PreparedStatement addressInsert = db.createPreparedStatement(addressQuery);
        PreparedStatement addressUpdate = db.createPreparedStatement(addressQuery2);

        if (defaultDeliveryAddress != null)
        {
            defaultDeliveryAddress.save(addressInsert, addressUpdate);
        }

        String cartQuery2 = "UPDATE cart SET customer_id = ? WHERE id = ?;";
        String cartQuery = "INSERT INTO cart(customer_id, id) SELECT ?, ? WHERE NOT EXISTS(SELECT * FROM cart WHERE id = ?);";
        PreparedStatement cartInsert = db.createPreparedStatement(cartQuery);
        PreparedStatement cartUpdate = db.createPreparedStatement(cartQuery2);
        cartUpdate.setString(1, ID);
        cartInsert.setString(1, ID);
        shoppingCart.save(db, cartInsert, cartUpdate);

        String wishlistQuery2 = "UPDATE wishlist SET name = ? WHERE id = ?;";
        String wishlistQuery = "INSERT INTO wishlist(id, name) SELECT ?, ? WHERE NOT EXISTS(SELECT * FROM wishlist WHERE name = ?);";
        PreparedStatement wishlistInsert = db.createPreparedStatement(wishlistQuery);
        PreparedStatement wishlistUpdate = db.createPreparedStatement(wishlistQuery2);

        for (WishList wl : wishLists)
        {
            cartUpdate.setString(1, ID);
            cartInsert.setString(1, ID);
            wl.save(db, wishlistInsert, wishlistUpdate, cartInsert, cartUpdate);
        }

        String orderQuery2 = "UPDATE \"order\" SET customer_id = ?, price = ?, status = ?, payment_method = ?, "
                             + "purchase_date = ?, dispatched_date = ?, cart_id = ?, delivery_address = ? WHERE id = ?;";
        String orderQuery = "INSERT INTO \"order\" (customer_id, id, price, status, payment_method, purchase_date, dispatched_date, cart_id, delivery_address)"
                            + " SELECT ?, ?, ?, ?, ?, ?, ?, ?, ? WHERE NOT EXISTS(SELECT * FROM \"order\" WHERE id = ?)";
        PreparedStatement orderInsert = db.createPreparedStatement(orderQuery);
        PreparedStatement orderUpdate = db.createPreparedStatement(orderQuery2);

        for (Order order : orders.values())
        {
            if (order.getStatus() == OrderStatus.UNPAID)
            {
                continue;
            }
            cartUpdate.setString(1, ID);
            cartInsert.setString(1, ID);
            orderUpdate.setString(1, ID);
            orderInsert.setString(1, ID);
            order.save(db, orderInsert, orderUpdate, cartInsert, cartUpdate, addressInsert, addressUpdate);
        }

        addressUpdate.executeBatch();
        addressInsert.executeBatch();

        cartUpdate.executeBatch();
        cartInsert.executeBatch();

        wishlistUpdate.executeBatch();
        wishlistInsert.executeBatch();

        orderUpdate.executeBatch();
        orderInsert.executeBatch();
    }

    /**
     * Adds a product to the shopping cart of a customer and returns the cart it
     * is added to.
     *
     * @param product The product to add to the cart.
     * @return Returns the cart the product was added to.
     */
    public Cart addToCart(Product product)
    {
        shoppingCart.addProduct(product);
        return shoppingCart;
    }

    @Override
    public String getID()
    {
        return ID;
    }

    /**
     * Gets all the wishlists of the customer.
     *
     * @return Returns a list of the wishlists the customer has.
     */
    public List<WishList> getWishLists()
    {
        return wishLists;
    }

    /**
     * Gets a specified wishlist.
     *
     * @param wishListIndex The index of the wishlist.
     * @return Returns the specified wishlist
     */
    public Cart getWishList(int wishListIndex)
    {
        return wishLists.get(wishListIndex);
    }

    /**
     * Creates a order based on a carts data.
     *
     * @return Returns the order that was created.
     */
    public CreateOrderResult createOrder()
    {
        if (this.email == null)
        {
            return new CreateOrderResult(CreateOrderErrors.NO_EMAIL);
        }
        Order order = new Order(shoppingCart, shoppingCart.getPrice());
        orders.put(order.getID(), order);
        return new CreateOrderResult(CreateOrderErrors.UNPAID, order.getID());
    }

    /**
     * Gets the customers shopping cart.
     *
     * @return Returns the customers shopping cart.
     */
    public Cart getShoppingCart()
    {
        return shoppingCart;
    }

    @Override
    public Order getOrder(Integer orderID)
    {
        return orders.get(orderID);
    }

    @Override
    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    @Override
    public String getSurname()
    {
        return surName;
    }

    public void setSurname(String surName)
    {
        this.surName = surName;
    }

    @Override
    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    @Override
    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String getPersonalizedData()
    {
        return personalizedData;
    }

    public void setDefaultDeliveryAddress(Address address)
    {
        defaultDeliveryAddress = address;
    }

    @Override
    public Address getDefaultDeliveryAddress()
    {
        return defaultDeliveryAddress;
    }
}
