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
    
    @Override
    public boolean equals(Object other)
    {
        if (other == this)
        {
            return true;
        }

        if (!(other instanceof ProductLine))
        {
            return false;
        }

        ProductLine pl = (ProductLine) other;

        return pl.product.equals(this.product) && pl.quantity == this.quantity;
    }
}
