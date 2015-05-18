package unifiedshoppingexperience;

import interfaces.CallBack;
import interfaces.OrderDTO;
import java.math.BigDecimal;
import java.util.Date;
import shared.OrderStatus;

/**
 * Keeps information about an order.
 *
 * @author Gruppe12
 */
public class Order implements OrderDTO
{

    private static int orderCreations;
    private final int orderNumber;
    private Date purchaseDate;
    private Date dispatchedDate;
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
        this.price = price;
        this.orderNumber = orderCreations + 1;
        this.orderLines = productLines;
        this.status = OrderStatus.UNPAID;
        this.purchaseDate = null;
        this.paymentMethod = null;
        this.deliveryAddress = null;
        this.dispatchedDate = null;
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

    public void setDispatchedDate()
    {
        dispatchedDate = new Date();
    }

    public void setDispatchedDate(Date date)
    {
        dispatchedDate = date;
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
            purchaseDate = new Date();
            this.status = OrderStatus.PAID;
            eventTrigger.call();
        };
        return cb;
    }

    @Override
    public int getID()
    {
        return orderNumber;
    }

    @Override
    public BigDecimal getPrice()
    {
        return price;
    }

    @Override
    public Date getPurchaseDate()
    {
        return purchaseDate;
    }

    @Override
    public Date getDispatchedDate()
    {
        return dispatchedDate;
    }

    @Override
    public int getOrderNumber()
    {
        return orderNumber;
    }

    @Override
    public String getPaymentMethod()
    {
        return paymentMethod;
    }

    @Override
    public String getAddress()
    {
        return deliveryAddress.toString();
    }

    @Override
    public OrderStatus getStatus()
    {
        return status;
    }
}
