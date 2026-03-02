public abstract class NotificationSender {
    protected final AuditLog audit;
    protected final SenderConfig config;

    protected NotificationSender(AuditLog audit, SenderConfig config) {
        this.audit = audit;
        this.config = config;
    }

    public final SendResult send(Notification n) {
        if (n.body == null || n.body.isEmpty()) return SendResult.failure("Body is empty");
        if (n.body.length() > config.maxLen) return SendResult.failure("Body too long");

        SendResult subValidation = validateSpecific(n);
        if (!subValidation.isSuccess()) {
            audit.add("Validation failed: " + subValidation.getError());
            return subValidation;
        }
        try {
            return sendNotification(n);
        } catch (Exception e) {
            audit.add("System Error: " + e.getMessage());
            return SendResult.failure("Unexpected error: " + e.getMessage());
        }
    }

    protected SendResult validateSpecific(Notification n) {
        return SendResult.success();
    }

    protected abstract SendResult sendNotification(Notification n);
}
