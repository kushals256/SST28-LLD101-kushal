public class StudentTaxPolicy implements TaxPolicy {
    
    @Override
    public double calculateTax(double amount) {
        return amount * 0.05;
    }
    
    @Override
    public double getPercent() {
        return 5.0;
    }
}
