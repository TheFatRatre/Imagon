package com.cyc.imagon.utils;

import org.springframework.util.StringUtils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;

public class StringUtil extends StringUtils {

    private static final String CHARS = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";

    public static boolean isBlank(String s) {
        return !hasText(s);
    }

    public static String getRandomCode(int count) {
        int len = CHARS.length();
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        StringBuilder sbr = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sbr.append(CHARS.charAt(random.nextInt(len)));
        }
        return sbr.toString();
    }


    public static String getHost(String url) {
        try {
            URI uri = new URI(url);
            return uri.getHost();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }


    public static String getLast(String str, String delimiter) {
        return str.substring(str.lastIndexOf(delimiter) + 1);
    }


    public static String getLast(String str) {
        return getLast(str, " ");
    }


    public static char lastChar(String str) {
        return str.charAt(str.length() - 1);
    }


    public static String removeLastChar(String str) {
        if (isBlank(str)) {
            return str;
        }
        return str.substring(0, str.length() - 1);
    }


    public static String replacePathLast(String path, String replace) {
       return path.substring(0, path.lastIndexOf("/") + 1) + replace;
    }


    public static String orElse(String val, String defaultVal) {
        return isBlank(val) ? defaultVal : val;
    }


    public static boolean between(char c, char min, char max) {
        return c >= min && c <= max;
    }


    public static boolean isLocalhost(String host) {
        return "127.0.0.1".equals(host) || "localhost".equals(host);
    }


    public static Integer toInt(String s) {
        return isBlank(s) ? null : Integer.valueOf(s);
    }

}