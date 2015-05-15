package unifiedshoppingexperience;

import interfaces.CallBack;
import interfaces.ICustomer;
import interfaces.IProduct;
import java.util.List;
import shared.CreateOrderErrors;
import shared.TestData;

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
        customers = new CustomerCollection();

        //WARNING HACKS AHOIST LADS
        assortment = new Assortment(TestData.loadProductMap(), TestData.loadTypeMap(), TestData.loadDescriptionMap());
        paymentManager = new PaymentManager();
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

    public CreateOrderErrors createOrder(String customerID)
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
    public List<IProduct> findProducts(String[] descriptionTags, String[] typeTags)
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

    public ICustomer getCustomer(String customerID)
    {
        return customers.getCustomer(customerID);
    }

    public String finishSale(String customerID, int orderID, String paymentMethod, Address address, CallBack eventTrigger)
    {
        Order o = customers.getCustomer(customerID).getOrder(orderID);

        o.setPaymentMethod(paymentMethod);
        o.setAddress(address);

        CallBack confirmPayment = o.getCallBack(eventTrigger);

        ProductLine[] productLines = o.getProductLines();

        return paymentManager.getPaymentProcessor(paymentMethod, confirmPayment, orderID, productLines, o.getPrice());
    }

    public CreateOrderErrors setEmail(String customerID, String email)
    {
        Customer customer = customers.getCustomer(customerID);
        customer.setEmail(email);
        return customer.createOrder();

    }
}
