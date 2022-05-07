package com.xfrenzy47x.app.helper;

import java.util.Scanner;

public class ScannerReader {
    private final Scanner scanner;
    public ScannerReader() {
        scanner = new Scanner(System.in);
    }

    public String readString() {
        return scanner.nextLine();
    }

    public double readDouble() {
        return Double.parseDouble(readString());
    }

    public int readInt() {
        return Integer.parseInt(readString());
    }
}
