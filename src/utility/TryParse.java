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

    /**
     * Attempts to parse a string to a integer.
     *
     * @param input The string to be parsed.
     * @return Returns true if the input can be parsed to an integer.
     */
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
