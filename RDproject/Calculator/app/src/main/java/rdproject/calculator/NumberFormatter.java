package rdproject.calculator;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

public class NumberFormatter {
    private static NumberFormatter formatter = new NumberFormatter(15);

    public static NumberFormatter getFormatter() {
        return formatter;
    }
    private int reqiuredLength;
    private DecimalFormat decimalFormat;
    public NumberFormatter(int reqiuredLength) {
        this.reqiuredLength = reqiuredLength;
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(' ');
        symbols.setDecimalSeparator('.');
        decimalFormat =  new DecimalFormat("#,###.###############", symbols);
        decimalFormat.setParseBigDecimal(true);
    }

    public boolean longerThan(String number, int digits) {
        return number.length() > digits;
    }
    public boolean longerThan(BigDecimal number, int digits) {
        return longerThan(number.toPlainString(), digits);
    }

    public String normalizeNumber(String number) {
        if (number.contains("+")) return number;
        BigDecimal bd = new BigDecimal("0");
        try {
            bd = (BigDecimal)decimalFormat.parse(number);
        } catch (ParseException e) {
            // do nothing
        }
        return bd.toString();
    }

    public String formatNumber(String number) {
        // int numLength = number.length();
        if (!longerThan(number, reqiuredLength)) {
            String string = decimalFormat.format(new BigDecimal(number));
            return string;
        }
        if (number.contains(".") && number.indexOf(".") < reqiuredLength){
            String string = decimalFormat.format(new BigDecimal(number));
            return string.substring(0, reqiuredLength);
        }
        String engineeringStr = number.substring(0, 1) + "." + number.substring(1, reqiuredLength-5);
        int exp = number.contains(".") ? number.indexOf(".") : number.length();
        engineeringStr+= "E+" + exp;
        return engineeringStr;
    }
}
