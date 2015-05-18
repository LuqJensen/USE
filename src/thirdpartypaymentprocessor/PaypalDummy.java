package thirdpartypaymentprocessor;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;

/**
 *
 * @author Gruppe12
 */
public class PaypalDummy
{
    private Socket socket;
    private DataOutputStream dos;

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

    public static void main(String[] args)
    {
        if (args.length != 1)
        {
            System.exit(1);
        }

        PaypalDummy pd = new PaypalDummy();
        pd.confirm(args[0]);
    }
}
