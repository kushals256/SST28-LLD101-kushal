import java.util.*;

public class DeviceRegistry {
    private final java.util.List<SmartClassroomDevice> devices = new ArrayList<>();

    public void add(SmartClassroomDevice d) { devices.add(d); }

    public <T extends SmartClassroomDevice> T getFirstOfType(Class<T> type) {
        for (SmartClassroomDevice d : devices) {
            if (type.isInstance(d)) {
                return type.cast(d);
            }
        }
        throw new IllegalStateException("Missing: " + type.getSimpleName());
    }
}
