package unifiedshoppingexperience;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A cart containing productLines and methods to handle these products. For example adding products to the cart.
 *
 * @author Gruppe12
 */
public abstract class Cart
{
    protected static int cartCreations;
    protected double price;
    protected int totalQuantity;
    protected int cartID;
    protected Map<Product, ProductLine> productLines;

    static
    {
        cartCreations = 0;
    }

    /**
     * Creates a cart and increments cartCreations for the system to know total amount of carts created.
     */
    public Cart()
    {
        this.cartID = cartCreations + 1;
        this.productLines = new HashMap<>();
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
}
