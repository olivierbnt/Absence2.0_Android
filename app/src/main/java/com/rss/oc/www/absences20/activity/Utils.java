package com.rss.oc.www.absences20.activity;

public class Utils {
    private static final char[] HEX = "0123456789ABCDEF".toCharArray();

    static String toHexString(byte[] bytes) {
        if (bytes.length == 0) {
            return "";
        }
        char[] chars = new char[bytes.length * 3];
        for (int i = 0; i < bytes.length; i++) {
            int c = bytes[i] & 0xFF;
            chars[i * 3] = HEX[c >>> 4];
            chars[i * 3 + 1] = HEX[c & 0x0F];
            chars[i * 3 + 2] = ' ';
        }
        return new String(chars).toLowerCase();
}

    static boolean isZeroed(byte[] bytes) {
        for (byte b : bytes) {
            if (b != 0x00) {
                return false;
            }
        }
        return true;
    }
}