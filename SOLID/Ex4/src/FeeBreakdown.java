public class FeeBreakdown {
    private double monthlyAmount;
    private double depositAmount;

    public FeeBreakdown(double monthlyAmount, double depositAmount) {
        this.monthlyAmount = monthlyAmount;
        this.depositAmount = depositAmount;
    }

    public double getMonthlyAmount() {
        return monthlyAmount;
    }

    public double getDepositAmount() {
        return depositAmount;
    }
}