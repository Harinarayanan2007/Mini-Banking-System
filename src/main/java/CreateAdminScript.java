import dao.UserDAO;
import util.PasswordUtil;
import util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class CreateAdminScript {
    public static void main(String[] args) {
        try {
            System.out.println("Connecting to database...");
            Connection conn = DBConnection.get();
            
            // Delete if already exists to prevent unique constraint errors
            try (PreparedStatement pstmt = conn.prepareStatement("DELETE FROM users WHERE email = 'admin@bank.com'")) {
                pstmt.executeUpdate();
            }

            System.out.println("Hashing password...");
            String hash = PasswordUtil.hash("admin123");
            
            System.out.println("Inserting Admin Account...");
            String sql = "INSERT INTO users (full_name, email, password_hash, role, status) VALUES ('Boss Admin', 'admin@bank.com', ?, 'ADMIN', 'ACTIVE')";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, hash);
                pstmt.executeUpdate();
            }
            
            System.out.println("SUCCESS! Admin account 'admin@bank.com' created with password 'admin123'.");
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
