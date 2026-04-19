public class TestEmail {
    public static void main(String[] args) throws Exception {
        try {
            System.out.println("Sending test email...");
            service.NotificationService.sendTransferSenderEmail(
                "narayananhari399@gmail.com", "Test User", "ACC123456789", new java.math.BigDecimal("500.00"));
            System.out.println("Email sent.");
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
