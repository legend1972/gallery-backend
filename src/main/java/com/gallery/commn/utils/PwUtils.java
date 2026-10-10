package com.gallery.commn.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PwUtils {
    private static final BCryptPasswordEncoder pwEncoder = new BCryptPasswordEncoder();

    public static String hashPw(String pw) {
        return pwEncoder.encode(pw);
    }

    public static boolean checkPw(String rawPw, String hashedPw) {
        return pwEncoder.matches(rawPw, hashedPw);
    }
}
