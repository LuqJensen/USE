package unifiedshoppingexperience;

import interfaces.IProduct;
import java.math.BigDecimal;

/**
 * Contains a product line, which is the product and the quantity of the
 * product.
 *
 * @author Gruppe12
 */
public class ProductLine
{
    private int quantity;
    private final Product product;

    /**
     * Creates a product line starting at a quantity of 1.
     *
     * @param product The product of the product line.
     */
    public ProductLine(Product product)
    {
        this.product = product;
        this.quantity = 1;
    }

    /**
     * Gets the product the product line describes.
     *
     * @return Returns the product the product line describes.
     */
    public IProduct getProduct()
    {
        return this.product;
    }

    /**
     * Increments the quantity of the product in the product line.
     */
    public void incrementQuantity()
    {
        ++quantity;
    }

    /**
     * Overrides the equals method so that it compares based on its attributes.
     *
     * @param other The referenced object with which to compare.
     * @return Returns true if the 2 objects have the same attribute values,
     */
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

        ProductLine pl = (ProductLine)other;

        return pl.product.equals(this.product) && pl.quantity == this.quantity;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public BigDecimal getTotalPrice()
    {
        return product.getPrice().multiply(new BigDecimal(quantity));
    }
}
