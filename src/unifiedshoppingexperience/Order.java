package unifiedshoppingexperience;

import java.util.Date;
import java.util.List;

/**
 * Keeps information about an order.
 *
 * @author Gruppe12
 */
public class Order
{
    private final int orderNumber;
    private final Date purchaseDate;
    private final Date deliveryDate;
    private final double amount;
    private static int orderCreations;
    private final ProductLine[] orderLines;

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
     * @param purchaseDate The date the payment was accepted.
     * @param deliveryDate The date the order was delivered to the customer.
     * @param amount The total cost of the order.
     */
    public Order(List<ProductLine> productLines, Date purchaseDate, Date deliveryDate, double amount)
    {

        this.purchaseDate = purchaseDate;
        this.deliveryDate = deliveryDate;
        this.amount = amount;
        this.orderNumber = orderCreations + 1;
        int productLinesAmount = productLines.size();
        orderLines = new ProductLine[productLinesAmount];
        for (int i = 0; i < productLinesAmount; ++i)
        {
            orderLines[i] = productLines.get(i);
        }
        incrementOrderCreations();
    }

    private static void incrementOrderCreations()
    {
        ++orderCreations;
    }
}
