package unifiedshoppingexperience;

import interfaces.CallBack;
import java.io.DataInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import thirdpartypaymentprocessor.PaypalDummy;

/**
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
        // Let JVM interrupt this thread on shutdown.
        // Apparantly the JVM cant handle this despite documentation saying it will...
        //t.setDaemon(true);
        // Instead we add a shutdown hook, a thread which is run at shutdown.
        /*Runtime.getRuntime().addShutdownHook(new Thread()
         {
         @Override
         public void run()
         {
         t.interrupt();
         }
         });*/
        // Neither of the above solutions appear to work. Probably because
        // we only shutdown the GUI thread and the business thread along with
        // the PaymentManager thread will continue running.
        // No graceful exists for us...
        t.start();
    }

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
                    BigDecimal postPrice = BigDecimal.valueOf(in.readDouble());

                    BigDecimal prePrice = orderPriceMap.get(orderID);

                    if (prePrice == null)
                    {
                        System.out.printf("Error: price of order %s is null.\n", orderID);
                    }
                    else if (prePrice.compareTo(postPrice) == 0)
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
                        System.out.printf("Order %s was not paid, preprocessed price: %s, postprocessed price: %s.\n", orderID, prePrice, postPrice);
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

    public String getPaymentProcessor(String paymentMethod, CallBack confirmPayment, int orderID, ProductLine[] productLines, BigDecimal price)
    {
        StringBuilder URLBuilder = new StringBuilder();
        URLBuilder.append("www.paypal.com/payment/");
        URLBuilder.append(orderID + "%%");
        StringBuilder productNamesBuilder = new StringBuilder();
        StringBuilder productQuantityBuilder = new StringBuilder();
        for (ProductLine productLine : productLines)
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

    public static void main(String[] test)
    {
        PaymentManager hda = new PaymentManager();
        ProductLine[] testProductLines = new ProductLine[2];
        testProductLines[0] = new ProductLine(new Product("AMDRocks", new BigDecimal(200.0), "type1", "AMD R Best"));
        testProductLines[1] = new ProductLine(new Product("nvidia", new BigDecimal(200.0), "type2", "sucks"));
        Order order = new Order(testProductLines, new BigDecimal(400.0));
        CallBack cb = () ->
        {
            System.out.println(order);
        };
        String URL = hda.getPaymentProcessor("visaDankort", cb, order.getID(), testProductLines, order.getPrice());
        PaypalDummy pd = new PaypalDummy();
        pd.confirm(URL.substring(URL.lastIndexOf("/") + 1));
    }
}
