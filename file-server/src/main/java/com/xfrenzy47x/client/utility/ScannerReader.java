package com.xfrenzy47x.client.utility;

import java.util.Scanner;

public class ScannerReader {
    Scanner scanner;
    public ScannerReader() {
        scanner = new Scanner(System.in);
    }

    public String readLine() {
        return scanner.nextLine();
    }

    public int readInt() {
        return Integer.parseInt(readLine());
    }
}

