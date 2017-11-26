package rdproject.calculator;

import java.math.BigDecimal;

public class Calc {
    private static final Calc calc = new Calc();

    public static Calc getCalc() {
        return calc;
    }

    private Calc() { }

    public BigDecimal plus(BigDecimal a, BigDecimal b) {

        return a.add(b).setScale(10, BigDecimal.ROUND_HALF_UP).stripTrailingZeros();
    }
    public BigDecimal minus(BigDecimal a, BigDecimal b) {
        return a.subtract(b).setScale(10, BigDecimal.ROUND_HALF_UP).stripTrailingZeros();
    }
    public BigDecimal multiply(BigDecimal a, BigDecimal b) {
        return a.multiply(b).setScale(10, BigDecimal.ROUND_HALF_UP).stripTrailingZeros();
    }
    public BigDecimal divide(BigDecimal a, BigDecimal b) {
        return a.divide(b,10, BigDecimal.ROUND_HALF_UP).stripTrailingZeros();
    }
    public BigDecimal x2 (BigDecimal a) {
        return a.multiply(a).setScale(10, BigDecimal.ROUND_HALF_UP).stripTrailingZeros();
    }
    public BigDecimal percent (BigDecimal a) {
        return a.divide(new BigDecimal(100), 10, BigDecimal.ROUND_HALF_UP).stripTrailingZeros();
    }

    public BigDecimal sqrt(BigDecimal num1) {
        double sqrt = num1.doubleValue();
        sqrt = Math.sqrt(sqrt);
        return new BigDecimal(sqrt).setScale(10, BigDecimal.ROUND_HALF_UP).stripTrailingZeros();
    }
}
