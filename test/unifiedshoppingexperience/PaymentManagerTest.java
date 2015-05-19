package unifiedshoppingexperience;

import interfaces.CallBack;
import java.math.BigDecimal;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Gruppe12
 */
public class PaymentManagerTest
{
    private static PaymentManager instance;

    @BeforeClass
    public static void setUpClass()
    {
        instance = new PaymentManager();
    }

    @Test
    public void testGetPaymentProcessor()
    {
        System.out.println("getPaymentProcessor");
        String paymentMethod = "Paypal";
        CallBack confirmPayment = () ->
        {
            System.out.println("We do not test this callback at this point in time.");
        };
        int orderID = 123;
        ProductLine[] productLines = new ProductLine[2];
        BigDecimal price = new BigDecimal("400.0");

        productLines[0] = new ProductLine(new Product("AMDRocks", new BigDecimal("200.0"), "type1", "AMD R Best"));
        productLines[1] = new ProductLine(new Product("nvidia", new BigDecimal("200.0"), "type2", "sucks"));

        String expResult = "www.paypal.com/payment/123%%AMDRocks%nvidia%%AMD R Best%sucks%%1%1%%" + price;
        System.out.println(expResult);
        String result = instance.getPaymentProcessor(paymentMethod, confirmPayment, orderID, productLines, price);
        System.out.println(result);
        assertEquals(expResult, result);
    }
}
