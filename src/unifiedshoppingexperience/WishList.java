package unifiedshoppingexperience;

import java.math.BigDecimal;
import java.util.Map;
import persistence.DataStore;

/**
 *
 * @author Gruppe12
 */
public class WishList extends Cart
{
    private String name;

    /**
     * Creates a wish list at runtime by creating a cart with a name.
     *
     * @param name The name of the wish list.
     */
    public WishList(String name, Customer c)
    {
        super();
        this.name = name;
        DataStore.getPersistence().persist(this, c);
    }

    /**
     * Creates a wish list at startup, this object is already persisted by the
     * database.
     *
     * @param cartID
     * @param price
     * @param productLines
     * @param name
     */
    public WishList(int cartID, BigDecimal price, Map<Product, ProductLine> productLines, String name)
    {
        super(cartID, price, productLines);
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
}
