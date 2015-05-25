package unifiedshoppingexperience;

import shared.Address;
import interfaces.CallBack;
import interfaces.CustomerDTO;
import interfaces.ProductDTO;
import interfaces.ProductLineDTO;
import java.util.List;
import persistence.DataStore;
import shared.CreateOrderResult;

/**
 * The controller handling communication between the presentation tier and logic
 * tier. The controller provides all the functions to the systems that are
 * relevant to the presentation tier and all use cases of the project is done
 * through the controller.
 *
 *
 * @author Gruppe12
 */
public class UnifiedShoppingExperience
{
    private static UnifiedShoppingExperience USE = null;

    private String email;
    private String phoneNumber;
    private CustomerCollection customers;
    private Assortment assortment;
    private PaymentManager paymentManager;

    /**
     * Creates the controller. The controller cant be called directly from
     * outside the class.
     */
    private UnifiedShoppingExperience()
    {
        email = "ESService@Electroshoppen.dk";
        phoneNumber = "28 52 57 40";

        DataStore.getPersistence().loadAll();

        paymentManager = new PaymentManager();

        customers = new CustomerCollection();
        assortment = new Assortment();

        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            @Override
            public void run()
            {
                shutdown();
            }
        });
    }

    public void shutdown()
    {
        DataStore.getPersistence().saveAll();
    }

    /**
     * Gets an instance of the controller. There can only be one instance
     * (Singleton) of the class, if there was already made an instance, that
     * instance is returned.
     *
     * @return Returns a instance of UnifiedShoppingExperience, if there was
     * already made an instance, that instance is returned.
     */
    public static UnifiedShoppingExperience getInstance()
    {
        if (USE == null)
        {
            USE = new UnifiedShoppingExperience();
        }
        return USE;
    }

    /**
     * Creates an order on the specified customer. The order created is based on
     * the customers shopping cart.
     *
     * @param customerID The ID of the customer.
     * @return Returns the status of this method and the order ID. The status
     * can be either UNPAID or NO_EMAIL. UNPAID means the method was
     * successfully executed. NO_EMAIL means the customer is not registered.
     */
    public CreateOrderResult createOrder(String customerID)
    {
        return customers.getCustomer(customerID).createOrder();
    }

    /**
     * A method for getting a list of products based on the type and description
     * tags. The list is sorted by relevance based on the description tags, and
     * filtered based on the type tags.
     *
     * @param descriptionTags The type map with keys pointing to a set of
     * products, based on type of product. For example the key "graphic card"
     * could point to a set of graphic cards.
     * @param typeTags The description map with keys pointing to a set of
     * products, based on how the product is described. For example the key
     * "980" would point to a set of products containing "980" in it's
     * description or title.
     * @return Returns a list of products sorted by relevance based on
     * description tags and filtered by type tags.
     */
    public List<ProductDTO> findProducts(String[] descriptionTags, String[] typeTags)
    {
        return assortment.findProducts(descriptionTags, typeTags);
    }

    /**
     * Adds a product to the shopping cart of a customer and returns the cart it
     * is added to.
     *
     * @param customerID The customerID of the customer that is adding a
     * product.
     * @param productModel The model name of the product.
     */
    public void addProduct(String customerID, String productModel)
    {
        customers.getCustomer(customerID).addToCart(assortment.getProduct(productModel));
    }

    /**
     * Gets a specified wish list of a specified customer.
     *
     * @param customerID The customer that owns the returned wish list.
     * @param wishListIndex The index of the wish list.
     * @return Returns the specified wish list.
     */
    public Cart getWishList(String customerID, int wishListIndex)
    {
        return customers.getCustomer(customerID).getWishList(wishListIndex);
    }

    /**
     * Gets the shopping cart of a specified customer.
     *
     * @param customerID The customers ID.
     * @return Returns the shopping cart owned by the specified customer.
     */
    public Cart getShoppingCart(String customerID)
    {
        return customers.getCustomer(customerID).getShoppingCart();
    }

    /**
     * Gets a registered customer by email.
     *
     * @param email The email of the customer.
     * @return Returns a registered customer.
     */
    public CustomerDTO getRegisteredCustomer(String email)
    {
        return customers.getRegisteredCustomer(email);
    }

    /**
     * Gets a customer by ID. It is not certain that the customer is registered.
     *
     * @param customerID The ID of the customer.
     * @return Returns a DTO for the customer.
     */
    public CustomerDTO getCustomer(String customerID)
    {
        return customers.getCustomer(customerID);
    }

    /**
     * Attempts to complete the sale by sending the customer to third party
     * payment processer.
     *
     * @param customerID The ID of the customer.
     * @param orderID The ID of the order.
     * @param paymentMethod The method used to pay for the order.
     * @param address The address the order is to be delivered to.
     * @param eventTrigger The eventtrigger that tells the UI the order was paid
     * for.
     * @return Returns the URL that needs to be opened if the order is to be
     * paid for.
     */
    public String finishSale(String customerID, int orderID, String paymentMethod, Address address, CallBack eventTrigger)
    {
        Customer c = customers.getCustomer(customerID);
        Order o = c.getOrder(orderID);

        c.setDefaultDeliveryAddress(address);
        o.setPaymentMethod(paymentMethod);
        o.setAddress(address);

        CallBack confirmPayment = o.getCallBack(eventTrigger);

        ProductLineDTO[] productLines = o.getProductLines();

        return paymentManager.getPaymentProcessor(paymentMethod, confirmPayment, orderID, productLines, o.getPrice());
    }

    /**
     * Gets the email of Electroshoppen.
     *
     * @return Returns the email of Electroshoppen
     */
    public String getEmail()
    {
        return email;
    }

    /**
     * Sets the email of a specified customer.
     *
     * @param customerID The ID of the customer.
     * @param email The email to be set.
     * @return Returns false if the email was not set.
     */
    public boolean setEmail(String customerID, String email)
    {
        if (email == null || email.isEmpty())
        {
            return false;
        }

        String[] splitEmail = email.split("@");
        if (email.endsWith("@") || splitEmail.length != 2 || splitEmail[0].equals(""))
        {
            return false;
        }

        String[] secondSplit = splitEmail[1].split("\\.");
        if (splitEmail[1].endsWith(".") || secondSplit.length != 2 || secondSplit[0].equals(""))
        {
            return false;
        }

        customers.setEmail(email, customerID);
        return true;
    }
}
