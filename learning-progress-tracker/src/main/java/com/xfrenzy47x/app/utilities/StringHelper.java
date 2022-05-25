package com.xfrenzy47x.app.utilities;

public class StringHelper {

    private StringHelper() {
        throw new IllegalStateException("Utility class");
    }
    public static boolean isValidEmail(String email) {
        boolean result = email.contains("@");

        if (result) {
            String[] parts = email.split("@");
            if (parts.length != 2) {
                result = false;
            } else if (!parts[parts.length-1].contains(".")) {
                result = false;
            } else {
                for (String part : parts) {
                    if (part.trim().length() == 0) {
                        result = false;
                        break;
                    }
                }
            }
        }


        return result;
    }

    public static boolean isValidNumber(String value) {
        boolean isValid = true;

        if (canParseToInteger(value)) {
            int converted = Integer.parseInt(value);
            if (converted < 0) {
                isValid = false;
            }
        } else {
            isValid = false;
        }

        return isValid;
    }

    private static boolean canParseToInteger(String value) {
        boolean isValid = true;
        try {
            Integer.parseInt(value);
        } catch (Exception ex) {
            isValid = false;
        }

        return isValid;
    }

    public static boolean notValidName(String name) {
        return name.length() < 2 || !containsCharacters(name);
    }

    private static boolean containsCharacters(String name) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ-' ";
        boolean result = true;
        char prevChar = '|';
        char currChar;
        name = name.toUpperCase();

        for (int i = 0; i < name.length(); i++) {
            boolean found = false;
            currChar = name.charAt(i);
            for (int j = 0; j < characters.length() && !found; j++) {
                found = name.charAt(i) == characters.charAt(j);
            }

            if (invalidSpecialCharacterPlacement(i, name)) {
                result = false;
            }

            if (prevChar == '|' || !doubledUpSpecialCharacter(prevChar, currChar)) {
                prevChar = currChar;
            } else {
                result = false;
            }

            if (!found) {
                result = false;
            }

            if (!result) {
                break;
            }
        }
        return result;
    }


    private static boolean doubledUpSpecialCharacter(char prev, char curr) {
        return (prev == '-' && curr == '-') || (curr == '\'' || prev == '\'');
    }

    private static boolean invalidSpecialCharacterPlacement(int i, String name) {
        return (i == 0 || i == name.length()-1) && (name.charAt(i) == '-' || name.charAt(i) == '\'');
    }
}

