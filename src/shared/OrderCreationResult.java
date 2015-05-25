package shared;

/**
 * Combining the status of the createOrder method with a Order ID.
 *
 * @author Gruppe12
 */
public class OrderCreationResult
{
    private final OrderCreationError error;
    private final int orderID;

    /**
     * Creates a CreaterOrderResult without a specific order ID.
     *
     * @param error The status of the creation of the order.
     */
    public OrderCreationResult(OrderCreationError error)
    {
        this.error = error;
        this.orderID = 0;
    }

    /**
     * Creates a CreaterOrderResult by combining the order ID and status.
     *
     * @param error The status of the creation of the order.
     * @param orderID The order ID.
     */
    public OrderCreationResult(OrderCreationError error, int orderID)
    {
        this.error = error;
        this.orderID = orderID;
    }

    /**
     * Gets the order ID of the order being created.
     *
     * @return Returns the order ID of the order being created
     */
    public int getOrderID()
    {
        return orderID;
    }

    /**
     * Gets the status of the creation of the order.
     *
     * @return The status of the creation of the order.
     */
    public OrderCreationError getError()
    {
        return error;
    }
}
