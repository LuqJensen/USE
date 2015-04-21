package unifiedshoppingexperience;

/**
 *
 * @author Gruppe12
 */
public class ShoppingCart extends Cart
{
    public ShoppingCart()
    {
        super();
    }
        public Cart addProduct(Product product)
    {
        ProductLine productLine = productLines.get(product);
        if (productLine != null)
        {
            productLine.incrementQuantity();
        }
        else
        {
            productLines.put(product, new ProductLine(1, product));
        }

        return this;
    }
}
