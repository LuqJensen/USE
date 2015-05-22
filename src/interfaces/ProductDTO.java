package interfaces;

import java.math.BigDecimal;

/**
 *
 * @author Gruppe12
 */
public interface ProductDTO
{
    /**
     * Gets the product model.
     *
     * @return The product model
     */
    public String getModel();

    /**
     * Gets the name of the product.
     *
     * @return The name of the product.
     */
    public String getName();

    /**
     * Gets the type of the product. "graphic card", "monitor" etc.
     *
     * @return The type of the product.
     */
    public String getType();

    /**
     * Gets the price of the product.
     *
     * @return The price of the product in BigDecimal, to avoid floating point
     * precision errors.
     */
    public BigDecimal getPrice();

    /**
     * Gets the product description.
     *
     * @return The product description
     */
    public String getDescription();
}
