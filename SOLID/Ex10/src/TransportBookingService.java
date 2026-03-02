public class TransportBookingService {
    // DIP violation: direct concretes
    private DistanceCalculator dist;
    private FareCalculator fareCalc;
    private DriverAllocator alloc;
    private PaymentGateway pay;
    private ConsoleUi ui;

    public TransportBookingService(DistanceCalculator dist, FareCalculator fareCalc, DriverAllocator alloc, PaymentGateway pay, ConsoleUi ui) {
        this.dist = dist;
        this.fareCalc = fareCalc;
        this.alloc = alloc;
        this.pay = pay;
        this.ui = ui;
    }

    public void book(TripRequest req) {
        double km = dist.km(req.from, req.to);
        ui.print("DistanceKm=" + km);

        String driver = alloc.allocate(req.studentId);
        ui.print("Driver=" + driver);

        double fare = fareCalc.calculateFare(km);

        String txn = pay.charge(req.studentId, fare);
        ui.print("Payment=PAID txn=" + txn);

        BookingReceipt r = new BookingReceipt("R-501", fare);
        ui.print("RECEIPT: " + r.id + " | fare=" + String.format("%.2f", r.fare));
    }
}
