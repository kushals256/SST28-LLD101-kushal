import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Hostel Fee Calculator ===");
        BookingRequest req = new BookingRequest(LegacyRoomTypes.DOUBLE, List.of(AddOn.LAUNDRY, AddOn.MESS, new LateFee(300)));
        HostelFeeCalculator calc = new HostelFeeCalculator(new FakeBookingRepo());
        calc.process(req);
    }
}
