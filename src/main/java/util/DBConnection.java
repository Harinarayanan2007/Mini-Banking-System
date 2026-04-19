package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static Connection conn;

    public static Connection get() throws SQLException {
        if (conn == null || conn.isClosed()) {
            String url = ConfigUtil.get("db.url");
            String user = ConfigUtil.get("db.user");
            String password = ConfigUtil.get("db.password");
            
            conn = DriverManager.getConnection(url, user, password);
        }
        return conn;
    }
}
