package util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {
    public static String hash(String rawPassword) {
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt());
    }

    public static boolean verify(String rawPassword, String hashed) {
        try {
            return BCrypt.checkpw(rawPassword, hashed);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
