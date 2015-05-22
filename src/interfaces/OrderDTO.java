package interfaces;

import java.math.BigDecimal;
import java.util.Date;
import shared.OrderStatus;

/**
 *
 * @author Gruppe12
 */
public interface OrderDTO
{

    /**
     * Gets the purchase date of the order.
     *
     * @return The purchase date of the order
     */
    public Date getPurchaseDate();

    /**
     * Gets the day the order was dispatched from the storage.
     *
     * @return The day the order was dispatched from the storage.
     */
    public Date getDispatchedDate();

    /**
     * Gets the order number
     *
     * @return The order number
     */
    public int getOrderNumber();

    /**
     * Gets the payment method used to pay for the order.
     *
     * @return The payment method used to pay for the order.
     */
    public String getPaymentMethod();

    /**
     * Gets the address the order was to be delivered to.
     *
     * @return The address the order is was to be delivered to.
     */
    public String getAddress();

    /**
     * Gets the status of the order. A status can be PAID, UNPAID or DISPATCHED.
     *
     * @return Returns the status of the order. A status can be PAID, UNPAID or
     * DISPATCHED.
     */
    public OrderStatus getStatus();

    /**
     * Gets the productlines within the order as a list.
     *
     * @return Returns the list of productLineDTO's within the order.
     */
    public ProductLineDTO[] getProductLines();

    /**
     * Gets the order ID.
     *
     * @return The order ID.
     */
    public int getID();

    /**
     * Gets the total price of the order.
     *
     * @return The total price of the order.
     */
    public BigDecimal getPrice();
}
