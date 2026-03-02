public class StaffTaxPolicy implements TaxPolicy {
    
    @Override
    public double calculateTax(double amount) {
        return amount * 0.02;
    }

    @Override
    public double getPercent() {
        return 2.0;
    }
}
