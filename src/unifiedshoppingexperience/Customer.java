package unifiedshoppingexperience;

import shared.Address;
import interfaces.CustomerDTO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import persistence.DataStore;
import shared.OrderCreationError;
import shared.OrderCreationResult;

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
    private String surname;
    private String email;
    private String phoneNumber;
    private ShoppingCart shoppingCart;
    private ArrayList<WishList> wishLists;
    private Map<Integer, Order> orders;
    private String personalizedData;
    private Address defaultDeliveryAddress;

    /**
     * Creates a customer at runtime with an ID
     *
     * @param ID The ID of the customer within the system.
     */
    public Customer(String ID)
    {
        this.ID = ID;
        this.shoppingCart = new ShoppingCart();
        this.wishLists = new ArrayList();
        this.orders = new HashMap();

        DataStore.getPersistence().persist(this);
    }

    /**
     * Creates a customer at startup, this object is already persisted by the
     * database.
     *
     * @param ID
     * @param firstName
     * @param surname
     * @param email
     * @param phoneNumber
     * @param address
     * @param shoppingCart
     * @param wishLists
     * @param orders
     * @throws SQLException
     */
    public Customer(String ID, String firstName, String surname, String email, String phoneNumber,
            Address address, ShoppingCart shoppingCart, ArrayList<WishList> wishLists, Map<Integer, Order> orders) throws SQLException
    {
        this.orders = new HashMap();
        this.wishLists = new ArrayList();
        this.ID = ID;
        this.firstName = firstName;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.defaultDeliveryAddress = address;
        this.shoppingCart = shoppingCart;
        this.wishLists = wishLists;
        this.orders = orders;
        //this.personalizedData = personalizedData;
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
    public OrderCreationResult createOrder()
    {
        if (this.email == null)
        {
            return new OrderCreationResult(OrderCreationError.NO_EMAIL);
        }
        Order order = new Order(shoppingCart, shoppingCart.getPrice(), this);
        shoppingCart = new ShoppingCart();
        orders.put(order.getID(), order);
        return new OrderCreationResult(OrderCreationError.UNPAID, order.getID());
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

    /**
     * Sets the first name of the customer.
     *
     * @param firstName The first name of the customer.
     */
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    @Override
    public String getSurname()
    {
        return surname;
    }

    /**
     * Sets a surname for the customer.
     *
     * @param surname The surname of the customer.
     */
    public void setSurname(String surname)
    {
        this.surname = surname;
    }

    @Override
    public String getEmail()
    {
        return email;
    }

    /**
     * Sets an email for the customer.
     *
     * @param email The email to be set.
     */
    public void setEmail(String email)
    {
        this.email = email;
    }

    @Override
    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    /**
     * Sets a phone number for the customer.
     *
     * @param phoneNumber The phone number to be set.
     */
    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String getPersonalizedData()
    {
        return personalizedData;
    }

    /**
     * Sets a default delivery address for the customer.
     *
     * @param address The default delivery address to be set.
     */
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
