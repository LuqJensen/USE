package unifiedshoppingexperience;

import interfaces.ProductLineDTO;
import java.math.BigDecimal;
import persistence.DataStore;

/**
 * Contains a product line, which is the product and the quantity of the
 * product.
 *
 * @author Gruppe12
 */
public class ProductLine implements ProductLineDTO
{
    private int quantity;
    private final Product product;

    /**
     * Creates a product line at runtime starting at a quantity of 1.
     *
     * @param product The product of the product line.
     * @param c
     */
    public ProductLine(Product product, Cart c)
    {
        this.product = product;
        this.quantity = 1;

        DataStore.getPersistence().persist(this, c);
    }

    /**
     * Creates a product line at startup, this object is already persisted by
     * the database.
     *
     * @param product
     * @param quantity
     */
    public ProductLine(Product product, Integer quantity)
    {
        this.product = product;
        this.quantity = quantity;
    }

    /**
     * Gets the product the product line describes.
     *
     * @return Returns the product the product line describes.
     */
    @Override
    public Product getProduct()
    {
        return this.product;
    }

    /**
     * Increases the quantity of this productLine with the inputted parameter.
     *
     * @param quantity The amount to increase the quantity with.
     */
    public void addQuantity(int quantity)
    {
        this.quantity += quantity;
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

    @Override
    public int getQuantity()
    {
        return quantity;
    }

    @Override
    public BigDecimal getTotalPrice()
    {
        return product.getPrice().multiply(new BigDecimal(quantity));
    }
}
