import util.DBConnection;
import java.sql.Connection;
import java.sql.Statement;

public class ResetDB {
    public static void main(String[] args) {
        try {
            Connection conn = DBConnection.get();
            try (Statement stmt = conn.createStatement()) {
                System.out.println("Clearing transactions...");
                stmt.executeUpdate("DELETE FROM transactions");
                
                System.out.println("Clearing accounts...");
                stmt.executeUpdate("DELETE FROM accounts");
                
                System.out.println("Clearing users...");
                stmt.executeUpdate("DELETE FROM users");
            }
            System.out.println("SUCCESS! Database completely wiped!");
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
