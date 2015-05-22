package unifiedshoppingexperience;

import interfaces.CartDTO;
import interfaces.ProductLineDTO;
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
public abstract class Cart implements CartDTO
{
    protected static int cartCreations;
    protected BigDecimal price;
    //protected int totalQuantity;
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

    public Cart(int cartID, BigDecimal price, Map<Product, ProductLine> productLines)
    {
        this.cartID = cartID;
        this.price = price;
        this.productLines = productLines;

        incrementCartCreations();
    }

    public Integer getID()
    {
        return this.cartID;
    }

    /**
     *
     * @return Returns all the product lines in the cart.
     */
    @Override
    public List<ProductLineDTO> getProducts()
    {
        return new ArrayList(productLines.values());
    }

    /**
     * Increments the amount of carts created. This is used to provide unique ID
     * for the carts.
     */
    private static void incrementCartCreations()
    {
        ++cartCreations;
    }

    @Override
    public BigDecimal getPrice()
    {
        return price;
    }
}
