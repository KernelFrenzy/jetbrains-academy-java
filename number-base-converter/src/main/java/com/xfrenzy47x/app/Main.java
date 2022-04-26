package com.xfrenzy47x.app;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Scanner;

public class Main {
    static Scanner scanner;
    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        handleFirstMenu();
    }

    public static void handleFirstMenu() {
        String action = "";
        while (!action.equals("/exit")) {
            System.out.println("Enter two numbers in format: {source base} {target base} (To quit type /exit)");
            action = scanner.nextLine();

            if (!action.equalsIgnoreCase("/exit")) {
                handleSecondMenu(action);
            }
            System.out.println();
        }
    }

    private static void handleSecondMenu(String action) {
        String[] options = action.split(" ");
        Integer source = Integer.parseInt(options[0]);
        Integer target = Integer.parseInt(options[1]);

        String stringNumber = "";
        while (!stringNumber.equals("/back")) {
            System.out.println("Enter number in base " + source + " to convert to base " + target + " (To go back type /back) ");
            stringNumber = scanner.nextLine();

            if (!stringNumber.equalsIgnoreCase("/back")) {
                handleConversion(stringNumber, source, target);
            }
            System.out.println();
        }
    }

    private static void handleConversion(String stringNumber, Integer source, Integer target) {
        StringBuilder theConvertedValue = new StringBuilder();

        if (stringNumber.contains(".")) {
            String[] numbers = stringNumber.split("\\.");
            String firstPart = new BigInteger(numbers[0], source).toString(target);

            BigDecimal total = BigDecimal.ZERO;
            int counter = 1;

            for (char a : numbers[1].toCharArray()) {
                total = total.add(BigDecimal.valueOf(Character.getNumericValue(a) * Math.pow(source, -counter)));
                counter++;
            }

            theConvertedValue.append(firstPart).append(".");

            for (int i = 0; i < 5; i++) {
                total = total.multiply(BigDecimal.valueOf(target));
                theConvertedValue.append(total.toBigInteger().toString(target));
                total = total.subtract(BigDecimal.valueOf(total.intValue()));
            }

        } else {
            theConvertedValue.append(new BigInteger(stringNumber, source).toString(target));
        }
        System.out.println("Conversion result: " + theConvertedValue);
    }
}
