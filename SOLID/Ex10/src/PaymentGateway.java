public interface PaymentGateway {
    String charge(String studentId, double amount);
}
