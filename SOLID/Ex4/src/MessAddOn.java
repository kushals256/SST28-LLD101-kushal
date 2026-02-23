public class MessAddOn implements PricingComponent {

    @Override
    public double monthlyAmount() {
        return 1000.0;
    }

    @Override
    public double depositAmount() {
        return 0.0;
    }

    @Override
    public String nameOf() {
        return "Mess";
    }
}