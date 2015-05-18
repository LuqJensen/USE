package interfaces;

import java.math.BigDecimal;

/**
 *
 * @author Gruppe12
 */
public interface ProductDTO
{
    public String getModel();

    public String getName();

    public String getType();

    public BigDecimal getPrice();

    public String getDescription();
}
