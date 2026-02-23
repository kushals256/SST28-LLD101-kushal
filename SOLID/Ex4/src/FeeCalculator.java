public class FeeCalculator {
    public static Money calculateMonthly(BookingRequest req) {
        double base = req.roomType.monthlyAmount();

        double add = 0.0;
        for (PricingComponent a : req.addOns) {
            add += a.monthlyAmount();
        }

        return new Money(base + add);
    }
}
