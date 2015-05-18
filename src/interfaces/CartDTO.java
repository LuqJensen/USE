/*
 *  © Frederik Ploug-Johansen
 */
package interfaces;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Frederik
 */
public interface CartDTO
{
    public List<ProductLineDTO> getProducts();

    public BigDecimal getPrice();
}
