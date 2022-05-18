package com.xfrenzy47x.app.util;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    private Validator() {
        throw new IllegalStateException("Utility class");
    }

    private static final Pattern pattern = Pattern.compile("^\\+?(\\(\\w+\\)|\\w+[ -]\\(\\w{2,}\\)|\\w+)([ -]\\w{2,})*");
    public static Character validGender(String value) {
        Character result = null;
        if (value.equalsIgnoreCase("m") || value.equalsIgnoreCase("f")) {
            result = value.toCharArray()[0];
        } else {
            System.out.println("Bad gender!");
        }
        return result;
    }

    public static LocalDate validBirthDate(String value) {
        LocalDate birthDate = null;
        try {
            if (!value.isEmpty())
                birthDate = LocalDate.parse(value);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (birthDate == null) {
            System.out.println("Bad birth date!");
        }
        return  birthDate;
    }

    public static String validNumber(String number) {
        Matcher matcher = pattern.matcher(number);
        String result = matcher.matches() && matcher.group().equals(number) ? number : "";
        if (result.isEmpty()) {
            System.out.println("Bad number!");
        }
        return number;
    }
}
