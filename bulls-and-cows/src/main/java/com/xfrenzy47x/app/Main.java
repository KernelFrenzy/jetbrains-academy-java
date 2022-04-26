package com.xfrenzy47x.app;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {
    static Scanner scanner;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        Integer codeLen = getCodeOrSymbolLength(true);
        if (codeLen == null) return;
        Integer symbolLen = getCodeOrSymbolLength(false);
        if (symbolLen == null) return;

        if (isValidInput(codeLen, symbolLen)) {
            String secretCode = generateUniqueNumberAndPrint(codeLen, symbolLen);

            System.out.println("Okay, let's start a game!");
            System.out.println(secretCode);

            int counter = 1;
            boolean gameFinished = false;
            while (!gameFinished) {
                System.out.println("Turn " + counter + ":");
                String input = scanner.nextLine();
                gameFinished = checkInput(input.toCharArray(), secretCode.toCharArray());
                counter++;
            }
        }

    }

    static Integer getCodeOrSymbolLength(boolean isCode) {
        System.out.println(isCode ? "Input the length of the secret code:" : "Input the number of possible symbols in the code:");
        String strCodeLen = scanner.nextLine();

        if (isValidNumber(strCodeLen)) {
            return Integer.parseInt((strCodeLen));
        } else {
            System.out.println("Error: \"" + strCodeLen + "\" isn't a valid number.");
            return null;
        }
    }

    static boolean isValidNumber(String value) {
        return value.matches("\\d+");
    }

    static boolean isValidInput(Integer codeLen, Integer symbolLen) {
        boolean continueGame = true;
        if (codeLen > symbolLen) {
            System.out.println("Error: it's not possible to generate a code with length of " + codeLen + " with " + symbolLen + " unique symbols.");
            continueGame = false;
        } else if (codeLen > 36 || symbolLen > 36) {
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            continueGame = false;
        }else if (symbolLen == 0 || codeLen == 0) {
            System.out.println("Error: ? ? ?");
            continueGame = false;
        }

        return continueGame;
    }

    static String generateUniqueNumberAndPrint(Integer codeLen, Integer symbolLen) {
        StringBuilder result = new StringBuilder();

        Random gen = new Random(47);
        while (result.length() != codeLen) {
            int theNumber = gen.nextInt(symbolLen+1);
            String value = new BigInteger((theNumber > 0 ? theNumber-1 : theNumber) + "").toString(symbolLen);
            while (result.toString().contains(value)) {
                theNumber = gen.nextInt(symbolLen+1);
                value = new BigInteger((theNumber > 0 ? theNumber-1 : theNumber) + "").toString(symbolLen);
            }
            result.append(value);
        }

        System.out.println("The secret is prepared: " + hashInput(result.toString()) + getMaxDigitAndSymbol(symbolLen));

        return result.toString();
    }

    static String getMaxDigitAndSymbol(Integer symbolLen) {
        StringBuilder result = new StringBuilder();
        result.append(" ");
        if (symbolLen > 9) {
            result.append("(0-9, ");
            String maxLetter = new BigInteger(symbolLen-1 + "").toString(symbolLen);
            if (symbolLen == 10) {
                maxLetter = "a";
            } else {
                maxLetter = "a-" + maxLetter;
            }

            result.append(maxLetter).append(")");
        } else {
            result.append("(0-").append(symbolLen).append(")");
        }

        return result.toString();
    }

    static String hashInput(String value) {
        StringBuilder result = new StringBuilder();
        for (char ignored : value.toCharArray()) {
            result.append("*");
        }
        return result.toString();
    }

    static boolean checkInput(char[] input, char[] secret) {
        int bulls = 0;
        int cows = 0;

        for (int i = 0; i < input.length; i++) {
            if (input[i] == secret[i]) {
                bulls++;
            } else if (Arrays.toString(secret).contains(input[i] + "")) {
                cows++;
            }
        }

        System.out.println(getGrade(bulls, cows));
        if (secret.length == bulls) {
            System.out.println("Congratulations! You guessed the secret code.");
            return true;
        }
        return false;
    }

    static String getGrade(int bulls, int cows) {
        String grade = "Grade: ";

        if (bulls > 0 && cows > 0) {
            grade += bulls + " bull" + returnPluralS(bulls) + " and " + cows + " cow" + returnPluralS(cows);
        } else if (bulls > 0) {
            grade += bulls + " bull" + returnPluralS(bulls);
        } else if (cows > 0) {
            grade += cows + " cow" + returnPluralS(cows);
        } else {
            grade += "None";
        }

        return grade;
    }

    static String returnPluralS(int value) {
        return value > 1 ? "s" : "";
    }
}

