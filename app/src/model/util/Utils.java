package model.util;

import java.text.DecimalFormat;
import java.text.ParseException;

public class Utils {

    public static float parseFloat(String value) throws ParseException {
        DecimalFormat format = new DecimalFormat();
        return format.parse(value).floatValue();
    }

    public static String formatNumber(Number value, int decimalPlaces) {
        DecimalFormat format = new DecimalFormat();
        format.setMaximumFractionDigits(decimalPlaces);
        return format.format(value);
    }

    public static String formatNumber(Number value) {
        return formatNumber(value, 2);
    }
}
