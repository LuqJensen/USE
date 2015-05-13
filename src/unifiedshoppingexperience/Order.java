package unifiedshoppingexperience;

import interfaces.CallBack;
import java.util.Date;
import shared.OrderStatus;

/**
 * Keeps information about an order.
 *
 * @author Gruppe12
 */
public class Order
{
    private static int orderCreations;
    private final int orderNumber;
    private final Date purchaseDate;
    private final Date deliveryDate;
    private final double amount;
    private final ProductLine[] orderLines;
    private String paymentMethod;
    private Address deliveryAddress;
    private OrderStatus status;

    static
    {
        orderCreations = 0;
    }

    /**
     * Creates an order with all product lines, purchase and delivery date and
     * total cost of order.
     *
     * @param productLines The product lines, could for example be product lines
     * copied from a cart.
     * @param amount The total cost of the order.
     */
    public Order(ProductLine[] productLines, double amount)
    {
        this.purchaseDate = new Date();
        this.deliveryDate = new Date();
        this.amount = amount;
        this.orderNumber = orderCreations + 1;
        this.orderLines = productLines;
        this.status = OrderStatus.UNPAID;
        this.paymentMethod = null;
        this.deliveryAddress = null;
        incrementOrderCreations();
    }

    private static void incrementOrderCreations()
    {
        ++orderCreations;
    }

    public void setPaymentMethod(String paymentMethod)
    {
        this.paymentMethod = paymentMethod;
    }

    public void setAddress(Address address)
    {
        this.deliveryAddress = address;
    }

    public ProductLine[] getProductLines() // getOrderLines?
    {
        return orderLines;
    }

    public CallBack getCallBack(CallBack eventTrigger)
    {
        // TODO IMPLEMENT this.
        return null;
    }

    public int getID()
    {
        return orderNumber;
    }

    public double getPrice() // maybe rename to getAmount()?
    {
        return amount;
    }
}
