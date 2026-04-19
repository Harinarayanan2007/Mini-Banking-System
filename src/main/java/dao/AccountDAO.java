package dao;

import java.sql.*;
import java.math.BigDecimal;
import model.Account;
import util.DBConnection;

public class AccountDAO {
    
    public void createAccount(int userId, String accountNo) throws SQLException {
        String sql = "INSERT INTO accounts (user_id, account_no, balance) VALUES (?, ?, 0.00)";
        try (PreparedStatement pstmt = DBConnection.get().prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, accountNo);
            pstmt.executeUpdate();
        }
    }

    public Account findByUserId(int userId) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE user_id = ?";
        try (PreparedStatement pstmt = DBConnection.get().prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Account account = new Account();
                    account.setId(rs.getInt("id"));
                    account.setUserId(rs.getInt("user_id"));
                    account.setAccountNo(rs.getString("account_no"));
                    account.setBalance(rs.getBigDecimal("balance"));
                    account.setOpenedAt(rs.getTimestamp("opened_at"));
                    return account;
                }
            }
        }
        return null;
    }

    public void updateBalance(int accountId, BigDecimal amountChange) throws SQLException {
        String sql = "UPDATE accounts SET balance = balance + ? WHERE id = ?";
        try (PreparedStatement pstmt = DBConnection.get().prepareStatement(sql)) {
            pstmt.setBigDecimal(1, amountChange);
            pstmt.setInt(2, accountId);
            pstmt.executeUpdate();
        }
    }
    
    public Account findById(int id) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE id = ?";
        try (PreparedStatement pstmt = DBConnection.get().prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Account account = new Account();
                    account.setId(rs.getInt("id"));
                    account.setUserId(rs.getInt("user_id"));
                    account.setAccountNo(rs.getString("account_no"));
                    account.setBalance(rs.getBigDecimal("balance"));
                    account.setOpenedAt(rs.getTimestamp("opened_at"));
                    return account;
                }
            }
        }
        return null;
    }

    public Account findByAccountNo(String accountNo) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE account_no = ?";
        try (PreparedStatement pstmt = DBConnection.get().prepareStatement(sql)) {
            pstmt.setString(1, accountNo);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Account account = new Account();
                    account.setId(rs.getInt("id"));
                    account.setUserId(rs.getInt("user_id"));
                    account.setAccountNo(rs.getString("account_no"));
                    account.setBalance(rs.getBigDecimal("balance"));
                    account.setOpenedAt(rs.getTimestamp("opened_at"));
                    return account;
                }
            }
        }
        return null;
    }
}
