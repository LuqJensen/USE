package thirdpartypaymentprocessor;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;

/**
 * Used for simulating a third party payment
 * processor.
 *
 * @author Gruppe12
 */
public class PaypalDummy
{
    private Socket socket;
    private DataOutputStream dos;

    /**
     * Creates a paypal dummy, used for simulating a third party payment
     * processor.
     */
    public PaypalDummy()
    {
        try
        {
            socket = new Socket("127.0.0.1", 43593);
            dos = new DataOutputStream(socket.getOutputStream());
        }
        catch (ConnectException ex)
        {
            System.out.println("Could not connect to 127.0.0.1:43593");
            System.exit(1);
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Confirms a order by providing the ID of the order to be confirmed, and
     * the amount of money paid.
     *
     * @param orderID ID of the order to be confirmed
     * @param price Amount of money paid
     */
    public void confirm(int orderID, double price)
    {
        try
        {
            dos.writeInt(orderID);
            dos.writeDouble(price);
            dos.flush();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Confirms an order based on a string (that simulates part of a URL)
     * provided by the requesting party that wishes to have a order paid for.
     * The string has agreed upon format, in this case it is agreed to split the
     * information by %%.
     * This is to simulate how the paypal would know in a real life scenario,
     * what information to send back to the requesting party.
     *
     * @param input The string that provides information about an order that was
     * requested to be paid for.
     */
    public void confirm(String input)
    {
        String[] split = input.split("%%");
        try
        {
            int orderID = Integer.parseInt(split[0]);
            double price = Double.parseDouble(split[4]);
            confirm(orderID, price);
        }
        catch (NumberFormatException ex)
        {
            ex.printStackTrace();
            System.exit(1);
        }
    }
}
