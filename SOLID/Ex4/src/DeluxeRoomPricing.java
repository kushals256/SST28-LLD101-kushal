public class DeluxeRoomPricing implements PricingComponent {
    
    @Override
    public double monthlyAmount() {
        return 16000.0;
    }

    @Override
    public double depositAmount() {
        return 5000.0;
    }

    @Override
    public String nameOf() {
        return "Deluxe Room";
    }
}
