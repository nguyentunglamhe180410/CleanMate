package com.example.cleanmate.common.utils;
import org.mindrot.jbcrypt.BCrypt;

public class HashPassword {
    // Work factor of 12 is a good balance between security and performance
    private static final int WORK_FACTOR = 12;

    public static String hashPassword(String plain) {
        return BCrypt.hashpw(plain, BCrypt.gensalt(WORK_FACTOR));
    }

    public static boolean verifyPassword(String plain, String hashed) {
        if (plain == null || hashed == null) return false;
        return BCrypt.checkpw(plain, hashed);
    }
}
