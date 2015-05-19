package interfaces;

import java.math.BigDecimal;

/**
 *
 * @author Gruppe12
 */
public interface ProductLineDTO
{
    public ProductDTO getProduct();

    public int getQuantity();

    public BigDecimal getTotalPrice();
}
