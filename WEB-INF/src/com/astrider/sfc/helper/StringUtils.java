package com.astrider.sfc.helper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public final class StringUtils {
    private StringUtils() {
    }

    public static boolean isNotEmpty(String arg) {
        return arg != null && !arg.isEmpty();
    }

    public static boolean isEmpty(String arg) {
        return arg != null && arg.isEmpty();
    }

    public static String toCamelCase(String arg) {
        return arg.substring(0, 1).toUpperCase() + arg.substring(1);
    }

    public static String getUniqueString(String arg) {
        return UUID.randomUUID().toString();
    }

    public static String getHash(String password) {
        StringBuilder builder = new StringBuilder();

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.reset();
            md.update(password.getBytes());
            byte[] digest = md.digest();

            for (int i = 0; i < digest.length; i++) {
                int d = digest[i] & 0xff;
                String hex = Integer.toHexString(d);
                if (hex.length() == 1) {
                    builder.append("0");
                }
                builder.append(hex);
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return builder.toString();
    }
}
