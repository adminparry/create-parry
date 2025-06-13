package com.example.demo.utils;


import java.security.SecureRandom;
import java.util.UUID;

public class RandomUtil {
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    public RandomUtil() {
    }

    public static String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);

        for(int i = 0; i < length; ++i) {
            sb.append((char)(SECURE_RANDOM.nextInt(26) + 97));
        }

        return sb.toString();
    }

    public static String generateRandomGUID() {
        return UUID.randomUUID().toString();
    }

    public static byte[] generateRandomBytes(int length) {
        if (length < 0) {
            throw new IllegalArgumentException("Count cannot be negative.");
        } else {
            byte[] bytes = new byte[length];
            SECURE_RANDOM.nextBytes(bytes);
            return bytes;
        }
    }

    public static String generateRandomFileName(int length, boolean includeTimestamp) {
        return includeTimestamp ? generateRandomString(length) + System.currentTimeMillis() : generateRandomString(length);
    }

    public static int nextInt(int startInclusive, int endExclusive) {
        if (endExclusive < startInclusive) {
            throw new IllegalArgumentException("Start value must be smaller or equal to end value.");
        } else if (startInclusive < 0) {
            throw new IllegalArgumentException("Both range values must be non-negative.");
        } else {
            return startInclusive == endExclusive ? startInclusive : startInclusive + SECURE_RANDOM.nextInt(endExclusive - startInclusive);
        }
    }

    public static int nextInt() {
        return nextInt(0, Integer.MAX_VALUE);
    }

    public static int nextIntEndInclusive(int endExclusive) {
        return nextInt(0, endExclusive);
    }

    public static int nextIntStartInclusive(int startInclusive) {
        return nextInt(startInclusive, Integer.MAX_VALUE);
    }

    public static byte[] generateRandomKey(int length) {
        byte[] key = new byte[length];
        (new SecureRandom()).nextBytes(key);
        return key;
    }
}
