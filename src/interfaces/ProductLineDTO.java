/*
 *  © Frederik Ploug-Johansen
 */
package interfaces;

import java.math.BigDecimal;

/**
 *
 * @author Frederik
 */
public interface ProductLineDTO
{
    public ProductDTO getProduct();

    public int getQuantity();

    public BigDecimal getTotalPrice();
}
