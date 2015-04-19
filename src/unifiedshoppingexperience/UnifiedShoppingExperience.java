package unifiedshoppingexperience;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Gruppe12
 */
public class UnifiedShoppingExperience
{
    private static UnifiedShoppingExperience instanceOfUSE = null;
    
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
        if (instanceOfUSE == null)
        {
            instanceOfUSE = new UnifiedShoppingExperience();
        }
        return instanceOfUSE;
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
    
    public List<Product> findProduct(String[] descriptionTags, String[] typeTags)
    {
        return assortment.findProducts(descriptionTags, typeTags);
    }

    public Cart addProduct(String customerID, Product product)
    {
        return getCustomer(customerID).addToCart(product);
    }

    public Cart viewWishList(String CustomerID, int wishListID)
    {
        return getCustomer(CustomerID).viewWishList(wishListID);
    }
    public Cart viewShoppingCart(String CustomerID)
    {
        return getCustomer(CustomerID).viewShoppingCart();
    }
}
