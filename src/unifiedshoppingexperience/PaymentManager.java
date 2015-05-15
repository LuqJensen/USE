package unifiedshoppingexperience;

import interfaces.CallBack;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Gruppe12
 */
public class PaymentManager
{

    Map<Integer, CallBack> callBackMap;
    Map<Integer, Double> orderPriceMap;

    public PaymentManager()
    {
        this.callBackMap = new HashMap();
    }

    public void startServer() throws IOException
    {
        ServerSocket listener = new ServerSocket(43595);
        try
        {
            while (true)
            {
                Socket socket = listener.accept();
                try
                {

                    DataInputStream in = new DataInputStream(socket.getInputStream());
                    int orderID = in.readInt();
                    double price = in.readDouble();
                    if (price == orderPriceMap.get(orderID))
                    {
                        CallBack confirmPayment = callBackMap.get(orderID);
                        confirmPayment.call();
                    }

                }
                finally
                {
                    socket.close();
                }

            }
        }
        finally
        {
            listener.close();
        }
    }

    public String getPaymentProcessor(String paymentMethod, CallBack confirmPayment, int orderID, ProductLine[] productLines, double price)
    {

        StringBuilder URLBuilder = new StringBuilder();
        URLBuilder.append("www.paypal.com/payment/");
        URLBuilder.append(orderID + "%%");
        StringBuilder productNamesBuilder = new StringBuilder();
        for (int i = 0; i < productLines.length; i++)
        {
            URLBuilder.append(productLines[i].getProduct().getModel() + "%");
            productNamesBuilder.append(productLines[i].getProduct().getName() + "%");
        }
        URLBuilder.append("%");
        URLBuilder.append(productNamesBuilder + "%");

        URLBuilder.append(price);
        callBackMap.put(orderID, confirmPayment);
        return URLBuilder.toString();
    }

    public static void main(String[] test)
    {
        PaymentManager hda = new PaymentManager();
        ProductLine[] hej = new ProductLine[2];
        hej[0] = new ProductLine(new Product("AMDRocks", 200, "type1", "AMD R Best"));
        hej[1] = new ProductLine(new Product("nvidia", 200, "type2", "sucks"));
        System.out.println(hda.getPaymentProcessor("visaDankort", null, 123, hej, 300));
    }
}
