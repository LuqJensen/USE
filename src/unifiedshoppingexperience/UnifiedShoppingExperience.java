package unifiedshoppingexperience;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private Map<String, Customer> customerMap;
    private Assortment assortment;

    /**
     * Creates the controller. The controller cant be called directly from
     * outside the class.
     */
    private UnifiedShoppingExperience()
    {
        email = "ESService@Electroshoppen.dk";
        phoneNumber = "28 52 57 40";
        customerMap = new HashMap();

        //WARNING HACKS AHOIST LADS
        assortment = new Assortment(TestData.loadProductMap(), TestData.loadTypeMap(), TestData.loadDescriptionMap());
    }

    /**
     * Gets a instance of the controller. There can only be one instance
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
     * Gets a customer based on the ID, if the ID doesn't point to any customer,
     * a new customer is created based on this ID.
     *
     * @param customerID The ID given to a customer within the system.
     * @return Returns a customer, based on the ID.
     */
    private Customer getCustomer(String customerID)
    {
        Customer customer = customerMap.get(customerID);

        if (customer == null)
        {
            customer = new Customer(customerID);
            customerMap.put(customerID, customer);
        }

        return customer;
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
    public List<Product> findProducts(String[] descriptionTags, String[] typeTags)
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
     * @return Returns the cart the product was added to.
     */
    public Cart addProduct(String customerID, String productModel)
    {
        return getCustomer(customerID).addToCart(assortment.getProduct(productModel));
    }

    /**
     * Gets a specified wish list of a specified customer.
     *
     * @param CustomerID The customer that owns the returned wish list.
     * @param wishListIndex The index of the wish list.
     * @return Returns the specified wish list.
     */
    public Cart getWishList(String CustomerID, int wishListIndex)
    {
        return getCustomer(CustomerID).getWishList(wishListIndex);
    }

    /**
     * Gets the shopping cart of a specified customer.
     *
     * @param CustomerID The customers ID.
     * @return Returns the shopping cart owned by the specified customer.
     */
    public Cart getShoppingCart(String CustomerID)
    {
        return getCustomer(CustomerID).getShoppingCart();
    }
}
