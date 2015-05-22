package interfaces;

import java.math.BigDecimal;

/**
 *
 * @author Gruppe12
 */
public interface ProductLineDTO
{
    /**
     * Gets the product in the product line.
     *
     * @return The product in the product line as a DTO.
     */
    public ProductDTO getProduct();

    /**
     * Gets the quantity of the product in the product line.
     *
     * @return Thbe quantity of the product in the product line.
     */
    public int getQuantity();

    /**
     * Gets the total price of the product line, ie quantity multiplied by the
     * price of the product.
     *
     * @return The total price of the product line.
     */
    public BigDecimal getTotalPrice();
}
