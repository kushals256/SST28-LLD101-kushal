public class LaundryAddOn implements PricingComponent {

    @Override
    public double monthlyAmount() {
        return 500.0;
    }

    @Override
    public double depositAmount() {
        return 0.0;
    }

    @Override
    public String nameOf() {
        return "Laundry";
    }
}