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

    public void setReqiuredLength(int reqiuredLength) {
        this.reqiuredLength = reqiuredLength;
        formatter = new NumberFormatter(reqiuredLength);
    }

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
  // problems with negative numbers
    public String formatNumber(String number) {
        String engineeringStr;
        boolean negative = number.startsWith("-");
        if (number.contains("+")) {        // this just in case we get engineering number here
             number = new BigDecimal(number).toPlainString();
        }

        if (!longerThan(number, reqiuredLength)) {
            String string = decimalFormat.format(new BigDecimal(number));
            return string;
        }
        if (number.contains(".") && number.indexOf(".") < reqiuredLength){
            String string = decimalFormat.format(new BigDecimal(number));
            return string.substring(0, reqiuredLength);
        }
       if (!negative) {
           engineeringStr = number.substring(0, 1) + "." + number.substring(1, reqiuredLength - 5);
           int exp = number.contains(".") ? number.indexOf(".") - 1 : number.length() - 1;
           engineeringStr += "E+" + exp;
       }
       else {
           engineeringStr = number.substring(0, 2) + "." + number.substring(2, reqiuredLength - 5);
           int exp = number.contains(".") ? number.indexOf(".") - 2 : number.length() - 2;
           engineeringStr += "E+" + exp;
       }
        return engineeringStr;
    }
}
