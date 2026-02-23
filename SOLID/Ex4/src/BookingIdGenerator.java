import java.util.Random;

public class BookingIdGenerator {
    public static String generate(BookingRequest req) {
        return "H-" + (7000 + new Random(1).nextInt(1000));
    }
}
