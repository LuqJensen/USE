package unifiedshoppingexperience;

import interfaces.CallBack;
import java.math.BigDecimal;
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
    private final BigDecimal price;
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
     * @param price The total cost of the order.
     */
    public Order(ProductLine[] productLines, BigDecimal price)
    {
        this.purchaseDate = new Date();
        this.deliveryDate = new Date();
        this.price = price;
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
        CallBack cb = () ->
        {
            this.status = OrderStatus.PAID;
            eventTrigger.call();
        };
        return cb;
    }

    public int getID()
    {
        return orderNumber;
    }

    public BigDecimal getPrice()
    {
        return price;
    }
}
