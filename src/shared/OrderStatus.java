package shared;

/**
 * The status of a order.
 *
 * @author Gruppe12
 */
public enum OrderStatus
{
    UNPAID,
    PAID,
    DISPATCHED;

    private static final OrderStatus[] values = OrderStatus.values();

    public static OrderStatus getValue(int index)
    {
        return values[index];
    }
}
