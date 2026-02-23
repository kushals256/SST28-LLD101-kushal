public class ReceiptPrinter {
    public static void print(BookingRequest req, Money monthly, Money deposit) {
        System.out.println("Room: " + req.roomType.nameOf() + " | AddOns: " + req.addOns.stream().map(PricingComponent::nameOf).reduce((a, b) -> a + ", " + b).orElse(""));
        System.out.println("Monthly: " + monthly);
        System.out.println("Deposit: " + deposit);
        System.out.println("TOTAL DUE NOW: " + monthly.plus(deposit));
    }
}
