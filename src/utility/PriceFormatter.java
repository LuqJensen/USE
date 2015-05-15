/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 *
 * @author Lucas
 */
public class PriceFormatter
{
    // Round double values to 2 decimal places and optionally show 1 leading and 2 trailing zeroes.
    // http://stackoverflow.com/questions/8819842/best-way-to-format-a-double-value-to-2-decimal-places
    // http://stackoverflow.com/questions/5054132/how-to-change-the-decimal-separator-of-decimalformat-from-comma-to-dot-point
    private static final DecimalFormat df = new DecimalFormat("0.00',-'", new DecimalFormatSymbols(Locale.ENGLISH));

    public static String format(double val)
    {
        return df.format(val);
    }

    public static String format(Double val)
    {
        return df.format(val);
    }

    public static String format(BigDecimal val)
    {
        return df.format(val.doubleValue());
    }
}
