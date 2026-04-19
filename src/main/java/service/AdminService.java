package service;

import dao.AccountDAO;
import dao.UserDAO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import model.User;
import util.DBConnection;

public class AdminService {
    private UserDAO userDAO = new UserDAO();
    private AccountDAO accountDAO = new AccountDAO();

    public List<User> getPendingAccounts() throws SQLException {
        return userDAO.findPendingUsers();
    }

    public void approveAccount(int userId) throws Exception {
        Connection conn = DBConnection.get();
        boolean autoCommit = conn.getAutoCommit();
        try {
            conn.setAutoCommit(false);
            userDAO.updateStatus(userId, "ACTIVE");
            User u = userDAO.findById(userId);
            // Gen Random length account
            String accNo = "ACC" + UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10).toUpperCase();
            accountDAO.createAccount(userId, accNo);

            conn.commit();
            
            NotificationService.sendApprovalEmail(u.getEmail(), u.getFullName(), accNo);
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(autoCommit);
        }
    }
    
    public void rejectAccount(int userId) throws Exception {
        userDAO.updateStatus(userId, "REJECTED");
        User u = userDAO.findById(userId);
        if (u != null) {
            NotificationService.sendRejectionEmail(u.getEmail(), u.getFullName());
        }
    }
}
