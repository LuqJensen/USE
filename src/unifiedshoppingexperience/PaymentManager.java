package unifiedshoppingexperience;

import interfaces.CallBack;
import interfaces.ProductLineDTO;
import java.io.DataInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import thirdpartypaymentprocessor.PaypalDummy;

/**
 * An active class running in a secondary thread that handles communication with
 * third party paytment processors.
 *
 * @author Gruppe12
 */
public class PaymentManager
{
    Map<Integer, CallBack> callBackMap;
    Map<Integer, BigDecimal> orderPriceMap;

    public PaymentManager()
    {
        this.callBackMap = new HashMap();
        this.orderPriceMap = new HashMap();
        Thread t = new Thread()
        {
            @Override
            public void run()
            {
                startServer();
            }
        };

        //t.setDaemon(true);
        /*Runtime.getRuntime().addShutdownHook(new Thread()
         {
         @Override
         public void run()
         {
         t.interrupt();
         }
         });*/
        t.start();
    }

    /**
     * Starts the server so its ready to communicate with third party payment
     * processors
     */
    public void startServer()
    {
        try (ServerSocket listener = new ServerSocket(43593))
        {
            while (true)
            {
                try (Socket socket = listener.accept())
                {
                    DataInputStream in = new DataInputStream(socket.getInputStream());
                    int orderID = in.readInt();
                    BigDecimal transferredMoney = BigDecimal.valueOf(in.readDouble());

                    BigDecimal prePrice = orderPriceMap.get(orderID);

                    if (prePrice == null)
                    {
                        System.out.printf("Error: price of order %s is null.\n", orderID);
                    }
                    else if (prePrice.compareTo(transferredMoney) == 0)
                    {
                        CallBack confirmPayment = callBackMap.get(orderID);

                        if (confirmPayment == null)
                        {
                            System.out.printf("Error: order callback of order %s is null.\n", orderID);
                            continue;
                        }

                        confirmPayment.call();
                        System.out.printf("Order %s was successfully paid.\n", orderID);
                    }
                    else
                    {
                        System.out.printf("Order %s was not paid, preprocessed price: %s, postprocessed price: %s.\n", orderID, prePrice, transferredMoney);
                    }
                }
                catch (IOException ex)
                {
                    ex.printStackTrace();
                }
            }
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     * Creates a URL based on information about specified order. This URL is
     * crucial to be able to confirm the right order, paid for with the right
     * amount of money.
     *
     * @param paymentMethod The payment method used to paying for the order.
     * @param confirmPayment The callback that sets the orders status to paid if
     * called.
     * @param orderID The ID of the order.
     * @param productLines The produce lines in the order.
     * @param price The total price of the order.
     * @return Returns the URL that needs to be opened if the order is to be
     * paid for.
     */
    public String getPaymentProcessor(String paymentMethod, CallBack confirmPayment, int orderID, ProductLineDTO[] productLines, BigDecimal price)
    {
        StringBuilder URLBuilder = new StringBuilder();
        URLBuilder.append("www.paypal.com/payment/");
        URLBuilder.append(orderID + "%%");

        StringBuilder productNamesBuilder = new StringBuilder();
        StringBuilder productQuantityBuilder = new StringBuilder();

        for (ProductLineDTO productLine : productLines)
        {
            URLBuilder.append(productLine.getProduct().getModel() + "%");
            productNamesBuilder.append(productLine.getProduct().getName() + "%");
            productQuantityBuilder.append(productLine.getQuantity() + "%");
        }
        URLBuilder.append("%");
        URLBuilder.append(productNamesBuilder + "%");
        URLBuilder.append(productQuantityBuilder + "%");
        URLBuilder.append(price);

        callBackMap.put(orderID, confirmPayment);
        orderPriceMap.put(orderID, price);
        return URLBuilder.toString();
    }

    /**
     * Integration test of the payment manager. This test has succeeded if the
     * hashcode of order is printed to the console
     *
     * @param test The arguments for the main method.
     */
    public static void main(String[] test)
    {
        PaymentManager pm = new PaymentManager();

        int orderID = 123;
        BigDecimal orderPrice = new BigDecimal(400.0);

        ProductLine[] testProductLines = new ProductLine[2];
        testProductLines[0] = new ProductLine(new Product("AMDRocks", new BigDecimal(200.0), "type1", "AMD R Best"), 1);
        testProductLines[1] = new ProductLine(new Product("nvidia", new BigDecimal(200.0), "type2", "sucks"), 2);

        CallBack cb = () ->
        {
            System.out.println(orderID + " integration test passed");
        };
        String URL = pm.getPaymentProcessor("paypal", cb, orderID, testProductLines, orderPrice);
        PaypalDummy pd = new PaypalDummy();
        pd.confirm(URL.substring(URL.lastIndexOf("/") + 1));
    }
}
