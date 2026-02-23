import java.util.*;

public class HostelFeeCalculator {
    private final FakeBookingRepo repo;

    public HostelFeeCalculator(FakeBookingRepo repo) { this.repo = repo; }

    public void process(BookingRequest req) {
        Money monthly = FeeCalculator.calculateMonthly(req);
        Money deposit = new Money(req.roomType.depositAmount());

        ReceiptPrinter.print(req, monthly, deposit);

        String bookingId = BookingIdGenerator.generate(req);
        repo.save(bookingId, req, monthly, deposit);
    }
}
