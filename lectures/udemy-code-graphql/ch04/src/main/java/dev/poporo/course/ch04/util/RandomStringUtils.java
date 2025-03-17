package dev.poporo.course.ch04.util;

import java.security.SecureRandom;

public class RandomStringUtils {
    private static final String ALPHANUMERIC_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String randomAlphanumeric(int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("Count must be greater than 0");
        }

        StringBuilder sb = new StringBuilder(count);
        for (int i = 0; i < count; i++) {
            int index = RANDOM.nextInt(ALPHANUMERIC_CHARACTERS.length());
            sb.append(ALPHANUMERIC_CHARACTERS.charAt(index));
        }
        return sb.toString();
    }
}
