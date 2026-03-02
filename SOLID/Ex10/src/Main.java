public class Main {
    public static void main(String[] args) {
        System.out.println("=== Transport Booking ===");
        TripRequest req = new TripRequest("23BCS1010", new GeoPoint(12.97, 77.59), new GeoPoint(12.93, 77.62));
        DistanceCalculator dist = new DistanceCalculatorImpl();
        FareCalculator fareCalc = new FareCalculatorImpl();
        DriverAllocator driverAlloc = new DriverAllocatorImpl();
        PaymentGateway payment = new PaymentGatewayImpl();
        ConsoleUi ui = new ConsoleUiImpl();

        // Mock dependencies for testing
        DriverAllocator driverAllocMock = new MockDriverAllocator();
        PaymentGateway paymentMock = new MockPaymentGateway();

        TransportBookingService svc = new TransportBookingService(dist, fareCalc, driverAllocMock, paymentMock, ui);
        svc.book(req);
    }
}
