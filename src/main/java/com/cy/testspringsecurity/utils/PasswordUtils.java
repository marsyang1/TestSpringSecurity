package com.cy.testspringsecurity.utils;

import com.google.common.hash.Hashing;

import java.nio.charset.Charset;

public class PasswordUtils {

    public static String encrypt(String password, String salt) {
        return Hashing.sha256().hashString(password + salt, Charset.defaultCharset()).toString();
    }
}
