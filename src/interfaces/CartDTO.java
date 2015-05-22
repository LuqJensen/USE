package interfaces;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Gruppe12
 */
public interface CartDTO
{
    /**
     * Gets the product in the cart as a list.
     *
     * @return Returns a list of the products in the cart.
     */
    public List<ProductLineDTO> getProducts();

    /**
     * Gets the total cost of all the products in the cart combined.
     *
     * @return Returns a BigDecimal value to avoid floating point precision
     * errors.
     */
    public BigDecimal getPrice();
}
