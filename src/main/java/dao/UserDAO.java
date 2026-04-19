package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.User;
import util.DBConnection;

public class UserDAO {
    
    public void insert(String fullName, String email, String passwordHash, String status, String role, String accountType) throws SQLException {
        String sql = "INSERT INTO users (full_name, email, password_hash, role, status, account_type) VALUES (?, ?, ?, CAST(? AS user_role), CAST(? AS account_status), ?)";
        try (PreparedStatement pstmt = DBConnection.get().prepareStatement(sql)) {
            pstmt.setString(1, fullName);
            pstmt.setString(2, email);
            pstmt.setString(3, passwordHash);
            pstmt.setString(4, role);
            pstmt.setString(5, status);
            pstmt.setString(6, accountType);
            pstmt.executeUpdate();
        }
    }

    public User findByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (PreparedStatement pstmt = DBConnection.get().prepareStatement(sql)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToUser(rs);
                }
            }
        }
        return null;
    }

    public User findById(int id) throws SQLException {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (PreparedStatement pstmt = DBConnection.get().prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToUser(rs);
                }
            }
        }
        return null;
    }

    public List<User> findPendingUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE status = 'PENDING' AND role = 'CUSTOMER'";
        try (Statement stmt = DBConnection.get().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                users.add(mapRowToUser(rs));
            }
        }
        return users;
    }

    public void updateStatus(int userId, String status) throws SQLException {
        String sql = "UPDATE users SET status = CAST(? AS account_status) WHERE id = ?";
        try (PreparedStatement pstmt = DBConnection.get().prepareStatement(sql)) {
            pstmt.setString(1, status);
            pstmt.setInt(2, userId);
            pstmt.executeUpdate();
        }
    }

    private User mapRowToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setFullName(rs.getString("full_name"));
        user.setEmail(rs.getString("email"));
        user.setPasswordHash(rs.getString("password_hash"));
        user.setRole(rs.getString("role"));
        user.setStatus(rs.getString("status"));
        user.setAccountType(rs.getString("account_type"));
        user.setCreatedAt(rs.getTimestamp("created_at"));
        return user;
    }
}
