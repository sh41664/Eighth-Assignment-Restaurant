package ap.restaurant.entities;

import ap.restaurant.model.User;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.SQLException;
import java.util.Base64;

public class UserManager {

    public static Object logIn(String username, String password) throws Exception {
        User foundUser = DBManager.findUser(username);
        if (foundUser != null) {
            String hashedPassword = hashPassword(password, foundUser.salt);
            if (foundUser.password.equals(hashedPassword)) {
                return foundUser;
            } else {
                return "wrong password";
            }
        } else {
            return "username or password is incorrect";
        }
    }

    public static Object signUp(String username, String password, String email) throws Exception {
        byte[] salt = generateSalt();
        String hashedPassword = hashPassword(password, salt);

        User existingUser = DBManager.findUser(username);
        if (existingUser != null) {
            return "User already exists";
        } else {
            int newId = DBManager.lastId("users");
            User newUser = new User();
            newUser.id = newId;
            newUser.username = username;
            newUser.password = hashedPassword;
            newUser.email = email;
            newUser.salt = salt;

            DBManager.addNewUser(newUser);
            return newUser;
        }
    }

    public static String hashPassword(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = factory.generateSecret(spec).getEncoded();
        return Base64.getEncoder().encodeToString(hash);
    }

    public static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }
}
