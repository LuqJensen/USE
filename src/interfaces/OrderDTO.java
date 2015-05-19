/*
 *  © Frederik Ploug-Johansen
 */
package interfaces;

import java.math.BigDecimal;
import java.util.Date;
import shared.OrderStatus;

/**
 *
 * @author Frederik
 */
public interface OrderDTO
{

    public Date getPurchaseDate();

    public Date getDispatchedDate();

    public int getOrderNumber();

    public String getPaymentMethod();

    public String getAddress();

    public OrderStatus getStatus();

    public ProductLineDTO[] getProductLines();

    public int getID();

    public BigDecimal getPrice();
}
