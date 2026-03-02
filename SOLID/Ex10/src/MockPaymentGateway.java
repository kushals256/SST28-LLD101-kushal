public class MockPaymentGateway implements PaymentGateway {
    public String charge(String studentId, double amount) {
        return "T-501";
    }

}
