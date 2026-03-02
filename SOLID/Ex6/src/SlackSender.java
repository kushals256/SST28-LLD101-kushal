public class SlackSender extends NotificationSender {
    public SlackSender(AuditLog audit, SenderConfig config) {
        super(audit, config);
    }

    public boolean validate(Notification n) {
        return n.email != null && !n.email.isEmpty();
    }

    @Override
    protected SendResult validateSpecific(Notification n) {
        if (!validate(n)) 
            return SendResult.failure("SLACK ERROR: missing recipient email");
        
        return SendResult.success();
    }

    @Override
    protected SendResult sendNotification(Notification n) {
        System.out.println("SLACK -> to=" + n.email + " body=" + n.body);
        audit.add("slack sent");
        return SendResult.success();
    }
}