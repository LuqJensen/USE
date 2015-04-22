package unifiedshoppingexperience;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
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

    private UnifiedShoppingExperience()
    {
        email = "ESService@Electroshoppen.dk";
        phoneNumber = "28 52 57 40";
        customerMap = new HashMap();
        // Hvordan vi skal have denne implementeret er jeg ikke lige sikker på.
        // Constructor for Assortment er skrevet også udkommenteret neden under
        //assortment = new Assortment();
        // Constructor som nævnt ovenover:
        //public Assortment(Map<String, Set<Product>> typeMap, Map<String, Set<Product>> descriptionMap)
    }

    public static UnifiedShoppingExperience getInstance()
    {
        if (USE == null)
        {
            USE = new UnifiedShoppingExperience();
        }
        return USE;
    }

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

    public List<Product> findProducts(String[] descriptionTags, String[] typeTags)
    {
        return assortment.findProducts(descriptionTags, typeTags);
    }

    public Cart addProduct(String customerID, String productModel)
    {
        return getCustomer(customerID).addToCart(assortment.getProduct(productModel));
    }

    public Cart getWishList(String CustomerID, int wishListIndex)
    {
        return getCustomer(CustomerID).getWishList(wishListIndex);
    }

    public Cart getShoppingCart(String CustomerID)
    {
        return getCustomer(CustomerID).getShoppingCart();
    }
}
