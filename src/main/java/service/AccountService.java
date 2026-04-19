package service;

import dao.AccountDAO;
import dao.TransactionDAO;
import dao.UserDAO;
import java.math.BigDecimal;
import java.util.UUID;
import util.PasswordUtil;
import java.sql.SQLException;
import java.sql.Connection;
import util.DBConnection;

public class AccountService {
    private UserDAO userDAO = new UserDAO();
    private AccountDAO accountDAO = new AccountDAO();
    private TransactionDAO transactionDAO = new TransactionDAO();

    public void register(String name, String email, String rawPassword, String accountType) throws Exception {
        if (userDAO.findByEmail(email) != null) {
            throw new Exception("Email already completely registered.");
        }
        String hash = PasswordUtil.hash(rawPassword);
        String role = "Admin Account".equals(accountType) ? "ADMIN" : "CUSTOMER";
        userDAO.insert(name, email, hash, "PENDING", role, accountType);
        NotificationService.sendPendingEmail(email, name);
    }

    public void deposit(int accountId, BigDecimal amount) throws Exception {
        Connection conn = DBConnection.get();
        boolean autoCommit = conn.getAutoCommit();
        try {
            conn.setAutoCommit(false);
            accountDAO.updateBalance(accountId, amount);
            transactionDAO.recordTransaction(accountId, "CREDIT", amount, "Deposit at branch");
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(autoCommit);
        }
    }
    
    public void withdraw(int accountId, BigDecimal amount) throws Exception {
        Connection conn = DBConnection.get();
        boolean autoCommit = conn.getAutoCommit();
        try {
            conn.setAutoCommit(false);
            accountDAO.updateBalance(accountId, amount.negate());
            transactionDAO.recordTransaction(accountId, "DEBIT", amount, "Withdrawal at branch");
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(autoCommit);
        }
    }
    
    public void transfer(int fromAccountId, String toAccountNo, BigDecimal amount) throws Exception {
        Connection conn = DBConnection.get();
        boolean autoCommit = conn.getAutoCommit();
        try {
            conn.setAutoCommit(false);
            
            model.Account toAccount = accountDAO.findByAccountNo(toAccountNo);
            if (toAccount == null) throw new Exception("Destination account not found.");
            if (toAccount.getId() == fromAccountId) throw new Exception("Cannot transfer to yourself.");
            
            model.Account fromAccount = accountDAO.findById(fromAccountId);
            if (fromAccount.getBalance().compareTo(amount) < 0) {
                throw new Exception("Insufficient balance for transfer.");
            }
            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                throw new Exception("Transfer amount must be positive.");
            }
            
            accountDAO.updateBalance(fromAccountId, amount.negate());
            transactionDAO.recordTransaction(fromAccountId, "DEBIT", amount, "Transfer to " + toAccountNo);
            
            accountDAO.updateBalance(toAccount.getId(), amount);
            transactionDAO.recordTransaction(toAccount.getId(), "CREDIT", amount, "Transfer from " + fromAccount.getAccountNo());
            
            conn.commit();
            
            try {
                model.User fromUser = userDAO.findById(fromAccount.getUserId());
                model.User toUser = userDAO.findById(toAccount.getUserId());
                if (fromUser != null) {
                    NotificationService.sendTransferSenderEmail(fromUser.getEmail(), fromUser.getFullName(), toAccount.getAccountNo(), amount);
                }
                if (toUser != null) {
                    NotificationService.sendTransferReceiverEmail(toUser.getEmail(), toUser.getFullName(), fromAccount.getAccountNo(), amount);
                }
            } catch (Exception e) {
                // Ignore email errors to prevent transaction rollback since commit already happened
                e.printStackTrace();
            }
            
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(autoCommit);
        }
    }
}
