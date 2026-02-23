public interface DiscountPolicy {
    double calculate(double subtotal, int distinctLines);
}
