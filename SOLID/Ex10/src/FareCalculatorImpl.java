public class FareCalculatorImpl implements FareCalculator {
    public double calculateFare(double km) {
        return 50.0 + km * 6.6666666667;
    }

}
