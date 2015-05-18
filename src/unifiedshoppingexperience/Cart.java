package unifiedshoppingexperience;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A cart containing productLines and methods to handle these products. For
 * example adding products to the cart.
 *
 * @author Gruppe12
 */
public abstract class Cart
{
    protected static int cartCreations;
    protected BigDecimal price;
    protected int totalQuantity;
    protected int cartID;
    protected Map<Product, ProductLine> productLines;

    static
    {
        cartCreations = 0;
    }

    /**
     * Creates a cart and increments cartCreations for the system to know total
     * amount of carts created.
     */
    public Cart()
    {
        this.cartID = cartCreations + 1;
        this.price = new BigDecimal(0.0);
        this.productLines = new HashMap();
        incrementCartCreations();
    }

    /**
     *
     * @return Returns all the product lines in the cart.
     */
    public List<ProductLine> getProducts()
    {
        return new ArrayList(productLines.values());
    }

    private static void incrementCartCreations()
    {
        ++cartCreations;
    }

    public BigDecimal getPrice()
    {
        return price;
    }
}
