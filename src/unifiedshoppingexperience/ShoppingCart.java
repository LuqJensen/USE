package unifiedshoppingexperience;

import java.math.BigDecimal;
import java.util.Map;
import persistence.DataStore;

/**
 * Contains appropriate methods for handling Shopping cart related actions such
 * as adding a product.
 *
 * @author Gruppe12
 */
public class ShoppingCart extends Cart
{
    /**
     * Creates a ShoppingCart at runtime and calls for the database to persist
     * this object.
     */
    public ShoppingCart()
    {
        super();
        DataStore.getPersistence().persist(this);
    }

    /**
     * Creates a ShoppingCart at startup, this object is already persisted by
     * the database.
     *
     * @param cartID
     * @param price
     * @param productLines
     */
    public ShoppingCart(int cartID, BigDecimal price, Map<Product, ProductLine> productLines)
    {
        super(cartID, price, productLines);
    }

    /**
     * Adds a product to the shopping cart.
     *
     * @param product The product to be added.
     */
    public void addProduct(Product product)
    {
        ProductLine productLine = productLines.get(product);
        if (productLine != null)
        {
            productLine.incrementQuantity();
        }
        else
        {
            productLines.put(product, new ProductLine(product, this));
        }

        // BigDecimal is immutable, must assign return value of method to the variable,
        // if not, the calculation is lost.
        price = price.add(product.getPrice());
    }
}
