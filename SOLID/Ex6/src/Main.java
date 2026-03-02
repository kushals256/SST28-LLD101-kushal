public class Main {
    public static void main(String[] args) {
        System.out.println("=== Notification Demo ===");
        AuditLog audit = new AuditLog();
        SenderConfig config = new SenderConfig();

        Notification n = new Notification("Welcome", "Hello and welcome to SST!", "riya@sst.edu", "9876543210");

        NotificationSender email = new EmailSender(audit, config);
        NotificationSender sms = new SmsSender(audit, config);
        NotificationSender wa = new WhatsAppSender(audit, config);
        NotificationSender slack = new SlackSender(audit, config);

        email.send(n);
        sms.send(n);
        wa.send(n);
        slack.send(n);

        System.out.println("AUDIT entries=" + audit.size());
    }
}
