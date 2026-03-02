public class SmartBoard implements InputControl {
    private boolean on;

    @Override public void powerOn() { on = true; }
    @Override public void powerOff() { on = false; System.out.println("SmartBoard OFF"); }
    @Override public void connectInput(String port) { if (on) System.out.println("SmartBoard ON (" + port + ")"); }
}
