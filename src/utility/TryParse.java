package utility;

/**
 *
 * @author Gruppe12
 */
public class TryParse
{
    private TryParse()
    {
    }

    public static boolean tryParseInteger(String input)
    {
        try
        {
            Integer.parseInt(input);
            return true;
        }
        catch (NumberFormatException ex)
        {
            return false;
        }
    }
}
