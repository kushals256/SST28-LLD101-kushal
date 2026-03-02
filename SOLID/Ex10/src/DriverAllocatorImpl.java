public class DriverAllocatorImpl implements DriverAllocator {
    public String allocate(String studentId) {
        // fake deterministic driver
        return "DRV-17";
    }
}
