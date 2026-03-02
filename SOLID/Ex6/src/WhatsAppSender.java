public class WhatsAppSender extends NotificationSender {
    public WhatsAppSender(AuditLog audit, SenderConfig config) {
        super(audit, config);
    }

    @Override
    protected SendResult validateSpecific(Notification n) {
        if (n.phone == null || !n.phone.startsWith("+")) 
            return SendResult.failure("WA Error: phone must start with + and country code");
        
        return SendResult.success();
    }

    @Override
    protected SendResult sendNotification(Notification n) {
        System.out.println("WA -> Sent to " + n.phone);
        audit.add("WA sent successfully");
        return SendResult.success();
    }
}
