package com.xfrenzy47x.app.util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class Helper {

    private Helper() {
        throw new IllegalStateException("Utility class");
    }
    public static String getPercentage(long x, int y) {
        return String.format("%.0f",((double) x / (double) y) * 100);
    }

    public static void print(String message, String stringOutputFile) {
        if (!message.isEmpty()) {
            if (stringOutputFile.isEmpty()) {
                System.out.println(message);
            } else {
                File outputFile = new File(stringOutputFile);
                try (PrintWriter printWriter = new PrintWriter(outputFile)) {
                    printWriter.println(message);
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }
}
