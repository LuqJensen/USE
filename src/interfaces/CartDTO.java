package interfaces;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Gruppe12
 */
public interface CartDTO
{
    public List<ProductLineDTO> getProducts();

    public BigDecimal getPrice();
}
