package unifiedshoppingexperience;

import java.util.Date;

/**
 *
 * @author Gruppe12
 */
public class Order
{
    private int orderNumber;
    private Date purchaseDate;
    private Date deliveryDate;
    private double amount;

    public Order(int orderNumber, Date purchaseDate, Date deliveryDate, double amount)
    {
        this.orderNumber = orderNumber;
        this.purchaseDate = purchaseDate;
        this.deliveryDate = deliveryDate;
        this.amount = amount;
    }
}
