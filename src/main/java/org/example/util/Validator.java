package org.example.util;

// utility class for validations - though most of this is already duplicated elsewhere
public class Validator {

    public static boolean isValidIsbn(String isbn) {
        if (isbn == null || isbn.isEmpty()) return false;
        return isbn.length() >= 10;
    }

    public static boolean isValidMemberId(String memberId) {
        if (memberId == null || memberId.isEmpty()) return false;
        return memberId.length() >= 3;
    }

    public static boolean isValidName(String name) {
        if (name == null || name.isEmpty()) return false;
        return name.length() >= 2;
    }
}