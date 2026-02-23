public class TripleRoomPricing implements PricingComponent {

    @Override
    public double monthlyAmount() {
        return 12000.0;
    }

    @Override
    public double depositAmount() {
        return 5000.0;
    }

    @Override
    public String nameOf() {
        return "Triple Room";
    }
}