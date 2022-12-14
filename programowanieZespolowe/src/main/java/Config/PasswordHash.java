package Config;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * The type Password hash.
 */
public class PasswordHash {
    /**
     * Password hash 2 string.
     *
     * @param password the password
     * @return the hash password
     */
    public String passwordHash2(String password) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }
}
