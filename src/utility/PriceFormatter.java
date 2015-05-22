package utility;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 *
 * @author Gruppe12
 */
public class PriceFormatter
{
    // Round BigDecimal values to 2 decimal places and optionally show 1 leading and 2 trailing zeroes.
    // http://stackoverflow.com/questions/8819842/best-way-to-format-a-double-value-to-2-decimal-places
    // http://stackoverflow.com/questions/5054132/how-to-change-the-decimal-separator-of-decimalformat-from-comma-to-dot-point
    private static final DecimalFormat df = new DecimalFormat("0.00',-'", new DecimalFormatSymbols(Locale.ENGLISH));
    private static final DecimalFormat dfDKK = new DecimalFormat("0.00' DKK'", new DecimalFormatSymbols(Locale.ENGLISH));

    /**
     * Formats the price to the "0.00',-'" format.
     *
     * @param val the value to be formated
     * @return Returns a formated price.
     */
    public static String format(BigDecimal val)
    {
        return df.format(val);
    }

    /**
     * Formats the price to the "0.00' DKK'" format.
     *
     * @param val the value to be formated
     * @return Returns a formated price.
     */
    public static String formatDKK(BigDecimal val)
    {
        return dfDKK.format(val);
    }
}
