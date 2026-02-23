import java.util.*;

public class BookingRequest {
    PricingComponent roomType;
    List<PricingComponent> addOns;

    public BookingRequest(PricingComponent roomType, List<PricingComponent> addOns) {
        this.roomType = roomType;
        this.addOns = addOns;
    }
}
