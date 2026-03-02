public class SendResult {
    private final boolean success;
    private final String error;

    private SendResult(boolean success, String error) {
        this.success = success;
        this.error = error;
    }

    public static SendResult success() { return new SendResult(true, null); }
    public static SendResult failure(String msg) { return new SendResult(false, msg); }

    public boolean isSuccess() { return success; }
    public String getError() { return error; }
}