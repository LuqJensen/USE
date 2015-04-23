package unifiedshoppingexperience;

/**
 * Contains appropriate methods for handling Shopping cart related actions such
 * as adding a product.
 *
 * @author Gruppe12
 */
public class ShoppingCart extends Cart
{
    /**
     * Creates a ShoppingCart same way a Cart is created.
     */
    public ShoppingCart()
    {
        super();
    }

    /**
     * Adds a product to the shopping cart.
     *
     * @param product The product to be added.
     * @return Returns the cart.
     */
    public Cart addProduct(Product product)
    {
        ProductLine productLine = productLines.get(product);
        if (productLine != null)
        {
            productLine.incrementQuantity();
        }
        else
        {
            productLines.put(product, new ProductLine(product));
        }

        return this;
    }
}
