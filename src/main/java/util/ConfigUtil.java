package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigUtil {
    private static final Properties properties = new Properties();

    static {
        try (InputStream in = ConfigUtil.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (in != null) {
                properties.load(in);
            } else {
                System.err.println("application.properties not found!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}
