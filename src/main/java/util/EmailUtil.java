package util;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailUtil {
    public static void sendEmail(String toEmail, String subject, String body) {
        boolean authEnabled = Boolean.parseBoolean(ConfigUtil.get("mail.smtp.auth"));
        if (!authEnabled) {
            // Stub it out for now since SMTP auth is disabled
            System.out.println("=== EMAIL STUB ===");
            System.out.println("To: " + toEmail);
            System.out.println("Subject: " + subject);
            System.out.println("Body: " + body);
            System.out.println("==================");
            return;
        }

        Properties props = new Properties();
        props.put("mail.smtp.host", ConfigUtil.get("mail.smtp.host"));
        props.put("mail.smtp.port", ConfigUtil.get("mail.smtp.port"));
        props.put("mail.smtp.auth", ConfigUtil.get("mail.smtp.auth"));
        props.put("mail.smtp.starttls.enable", ConfigUtil.get("mail.smtp.starttls.enable"));
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");

        String user = ConfigUtil.get("mail.user");
        String pass = ConfigUtil.get("mail.password");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, pass);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(body);

            new Thread(() -> {
                try {
                    System.out.println("Attempting to send email to " + toEmail + "...");
                    Transport.send(message);
                    System.out.println("Email successfully sent to " + toEmail);
                } catch (MessagingException e) {
                    System.err.println("Failed to send email to " + toEmail);
                    e.printStackTrace();
                }
            }).start();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
