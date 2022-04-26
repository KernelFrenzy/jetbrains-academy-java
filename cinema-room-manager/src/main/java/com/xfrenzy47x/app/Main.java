package com.xfrenzy47x.app;

import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static int[][] seats;
    static int totalIncome;
    static int purchasedTickets;
    static int currentIncome;

    public static void main(String[] args) {
        init();
    }

    private static void init() {
        System.out.println("Enter the number of rows: ");
        int rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row: ");
        int rowCount = scanner.nextInt();
        System.out.println();

        seats = new int[rows][rowCount];

        purchasedTickets = 0;
        currentIncome = 0;

        calculateTotalIncome();
        menu();
    }

    private static void menu() {
        int action;
        do {
            System.out.println("1. Show the seats");
            System.out.println("2. Buy a ticket");
            System.out.println("3. Statistics");
            System.out.println("0. Exit");
            action = scanner.nextInt();

            System.out.println();
            handleAction(action);
            System.out.println();
        } while (action > 0);
    }

    private static void handleAction(int action) {
        switch (action) {
            case 1 -> printLayout();
            case 2 -> purchaseTicket();
            case 3 -> printStats();
            default -> doNothing();
        }
    }

    private static void doNothing() {
        // This is empty, because sonar wants a default and this app didn't require extensive error handling for it to be required.
    }

    private static void printStats() {
        System.out.println("Number of purchased tickets: " + purchasedTickets);
        System.out.println("Percentage: " + getPercentage());
        System.out.println("Current income: $" + currentIncome);
        System.out.println("Total income: $" + totalIncome);
    }

    private static String getPercentage() {
        double percentage = ((double)purchasedTickets / (double)(seats.length * seats[0].length)) * 100;
        return String.format("%.2f", percentage) + "%";
    }


    private static void purchaseTicket() {
        int row;
        int seat;
        do {
            System.out.println("Enter a row number: ");
            row = scanner.nextInt();
            System.out.println("Enter a seat number in that row:");
            seat = scanner.nextInt();
            System.out.println();
            if (validInput(row, seat)) {
                break;
            }
            System.out.println();
        } while (true);

        int price = getTicketPrice(row);
        System.out.println("Ticket price: $" + price);
        seats[row-1][seat-1] = 1;
        purchasedTickets += 1;
        currentIncome += price;
    }

    private static boolean validInput(int row, int seat) {
        boolean result = true;

        if (row > seats.length || seat > seats[0].length) {
            System.out.println("Wrong input!");
            result = false;
        } else if (seats[row-1][seat-1] > 0) {
            System.out.println("That ticket has already been purchased!");
            result = false;
        }

        return result;
    }


    private static void printLayout() {
        System.out.println("Cinema: ");
        StringBuilder theLayout = new StringBuilder();
        StringBuilder firstRow = new StringBuilder();
        firstRow.append("  ");

        for (int i = 0; i < seats[0].length; i++) {
            firstRow.append(i + 1).append(" ");
        }

        theLayout.append(firstRow).append("\n");

        for (int i = 0; i < seats.length; i++) {
            StringBuilder theLine = new StringBuilder();
            for (int j = 0; j < seats[i].length; j++) {
                theLine.append((j == 0) ? (i+1) : "");
                theLine.append(" ").append(isTaken(seats[i][j]));
            }
            theLayout.append(theLine).append("\n");
        }

        System.out.println(theLayout);
    }

    private static String isTaken(int value) {
        return value > 0 ? "B" : "S";
    }

    private static int getTicketPrice(int row) {
        int price;
        if (seats.length * seats[0].length <= 60) {
            price = 10;
        } else {
            int half = seats.length / 2;
            price = row > half ? 8 : 10;
        }
        return price;
    }

    private static void calculateTotalIncome() {
        totalIncome = 0;
        int rows = seats.length;
        int rowCount = seats[0].length;
        if (seats.length * rowCount <= 60) {
            totalIncome = rows * rowCount * 10;
        } else {
            int half = rows / 2;
            totalIncome += half * rowCount * 10;
            totalIncome += (rows-half) * rowCount * 8;
        }
    }
}
