public class DefaultTaxPolicy implements TaxPolicy {
    
    @Override
    public double calculateTax(double amount) {
        return amount * 0.08;
    }

    @Override
    public double getPercent() {
        return 8.0;
    }
}
