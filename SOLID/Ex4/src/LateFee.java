public class LateFee implements PricingComponent {
    private double fee;
    public LateFee(double fee) { this.fee = fee; }

    @Override
    public double monthlyAmount() {
        return fee;
    }

    @Override
    public double depositAmount() {
        return 0.0;
    }

    @Override
    public String nameOf() {
        return "Late Fee";
    }
    
}
