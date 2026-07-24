package com.financialpilot.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    // Private constructor because this is a utility class
    private PasswordUtil() {
    }

    // Hash password before storing
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    // Verify entered password against stored hash
    public static boolean verifyPassword(String enteredPassword,
                                         String storedHash) {

        return BCrypt.checkpw(enteredPassword, storedHash);
    }
}