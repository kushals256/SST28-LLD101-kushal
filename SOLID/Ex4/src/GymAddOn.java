public class GymAddOn implements PricingComponent {

    @Override
    public double monthlyAmount() {
        return 300.0;
    }

    @Override
    public double depositAmount() {
        return 0.0;
    }

    @Override
    public String nameOf() {
        return "Gym";
    }
}