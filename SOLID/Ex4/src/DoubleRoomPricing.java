public class DoubleRoomPricing implements PricingComponent {

    @Override
    public double monthlyAmount() {
        return 15000.0;
    }

    @Override
    public double depositAmount() {
        return 5000.0;
    }

    @Override
    public String nameOf() {
        return "Double Room";
    }
}