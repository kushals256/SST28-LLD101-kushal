public class SmsSender extends NotificationSender {
    public SmsSender(AuditLog audit, SenderConfig config) {
        super(audit, config);
    }

    @Override
    protected SendResult validateSpecific(Notification n) {
        if (n.phone == null) 
            return SendResult.failure("SMS ERROR: phone must not be null");
        
        return SendResult.success();
    }

    @Override
    protected SendResult sendNotification(Notification n) {
        System.out.println("SMS -> to=" + n.phone + " body=" + n.body);
        audit.add("sms sent");
        return SendResult.success();
    }
}