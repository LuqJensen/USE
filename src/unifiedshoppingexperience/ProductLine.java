package unifiedshoppingexperience;

/**
 *
 * @author Gruppe12
 */
public class ProductLine
{
    private int quantity;
    private Product product;

    public ProductLine(Product product)
    {
        this.product = product;
        this.quantity = 1;
    }

    public Product getProduct()
    {
        return this.product;
    }

    public void incrementQuantity()
    {
        ++quantity;
    }
}
