public class SingleRoomPricing implements PricingComponent {

    @Override
    public double monthlyAmount() {
        return 14000.0;
    }

    @Override
    public double depositAmount() {
        return 5000.0;
    }

    @Override
    public String nameOf() {
        return "Single Room";
    }
}