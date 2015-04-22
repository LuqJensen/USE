package unifiedshoppingexperience;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
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

    public Cart()
    {
        this.cartID = cartCreations;
        ++cartCreations;
        this.productLines = new HashMap<>();
    }

    public List<ProductLine> getProducts()
    {
        return new ArrayList(productLines.values());
    }
}
