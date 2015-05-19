package shared;

/**
 *
 * @author Gruppe12
 */
public class CreateOrderResult
{
    private CreateOrderErrors error;
    private int orderID;

    public CreateOrderResult(CreateOrderErrors error)
    {
        this.error = error;
        this.orderID = 0;
    }

    public CreateOrderResult(CreateOrderErrors error, int orderID)
    {
        this.error = error;
        this.orderID = orderID;
    }

    public int getOrderID()
    {
        return orderID;
    }

    public CreateOrderErrors getError()
    {
        return error;
    }
}
