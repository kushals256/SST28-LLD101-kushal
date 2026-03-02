public class ClassroomController {
    private final DeviceRegistry reg;

    public ClassroomController(DeviceRegistry reg) { this.reg = reg; }

    public void startClass() {
        InputControl pj = reg.getFirstOfType(Projector.class);
        pj.powerOn();
        pj.connectInput("HDMI-1");

        BrightnessControl lights = reg.getFirstOfType(LightsPanel.class);
        lights.setBrightness(60);

        TemperatureControl ac = reg.getFirstOfType(AirConditioner.class);
        ac.setTemperatureC(24);

        AttendanceControl scan = reg.getFirstOfType(AttendanceScanner.class);
        System.out.println("Attendance scanned: present=" + scan.scanAttendance());

        SmartBoard sb = reg.getFirstOfType(SmartBoard.class);
        sb.powerOn();
        sb.connectInput("HDMI-2");
    }

    public void endClass() {
        System.out.println("Shutdown sequence:");
        reg.getFirstOfType(Projector.class).powerOff();
        reg.getFirstOfType(LightsPanel.class).powerOff();
        reg.getFirstOfType(AirConditioner.class).powerOff();
        reg.getFirstOfType(SmartBoard.class).powerOff();
    }
}
