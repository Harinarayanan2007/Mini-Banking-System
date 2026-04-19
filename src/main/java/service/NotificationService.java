package service;

import util.EmailUtil;

public class NotificationService {
    
    public static void sendPendingEmail(String email, String name) {
        String subject = "Account Pending Approval";
        String body = "Hello " + name + ",\n\nYour account has been registered and is pending approval by an administrator. You will be notified once activated.";
        EmailUtil.sendEmail(email, subject, body);
    }

    public static void sendApprovalEmail(String email, String name, String accountNo) {
        String subject = "Account Approved";
        String body = "Hello " + name + ",\n\nYour account has been approved! You can now log in and use our banking services.\n\nYour Account Number is: " + accountNo;
        EmailUtil.sendEmail(email, subject, body);
    }

    public static void sendRejectionEmail(String email, String name) {
        String subject = "Account Registration Rejected";
        String body = "Hello " + name + ",\n\nWe regret to inform you that your account registration has been rejected by an administrator. Please contact support for more details.";
        EmailUtil.sendEmail(email, subject, body);
    }

    public static void sendTransferSenderEmail(String email, String name, String toAccountNo, java.math.BigDecimal amount) {
        String subject = "Transfer Successful";
        String body = "Hello " + name + ",\n\nYou have successfully transferred $" + amount.toString() + " to Account No: " + toAccountNo + ".\nThank you for using our banking services.";
        EmailUtil.sendEmail(email, subject, body);
    }

    public static void sendTransferReceiverEmail(String email, String name, String fromAccountNo, java.math.BigDecimal amount) {
        String subject = "Amount Received";
        String body = "Hello " + name + ",\n\nYou have received $" + amount.toString() + " from Account No: " + fromAccountNo + ".\nThank you for using our banking services.";
        EmailUtil.sendEmail(email, subject, body);
    }
}
