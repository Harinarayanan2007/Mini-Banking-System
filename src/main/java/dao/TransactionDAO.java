package dao;

import java.sql.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import model.Transaction;
import util.DBConnection;

public class TransactionDAO {
    
    public void recordTransaction(int accountId, String type, BigDecimal amount, String description) throws SQLException {
        String sql = "INSERT INTO transactions (account_id, type, amount, description) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = DBConnection.get().prepareStatement(sql)) {
            pstmt.setInt(1, accountId);
            pstmt.setString(2, type);
            pstmt.setBigDecimal(3, amount);
            pstmt.setString(4, description);
            pstmt.executeUpdate();
        }
    }

    public List<Transaction> findByAccountId(int accountId) throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE account_id = ? ORDER BY txn_time DESC";
        try (PreparedStatement pstmt = DBConnection.get().prepareStatement(sql)) {
            pstmt.setInt(1, accountId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Transaction txn = new Transaction();
                    txn.setId(rs.getInt("id"));
                    txn.setAccountId(rs.getInt("account_id"));
                    txn.setType(rs.getString("type"));
                    txn.setAmount(rs.getBigDecimal("amount"));
                    txn.setDescription(rs.getString("description"));
                    txn.setTxnTime(rs.getTimestamp("txn_time"));
                    transactions.add(txn);
                }
            }
        }
        return transactions;
    }
}
