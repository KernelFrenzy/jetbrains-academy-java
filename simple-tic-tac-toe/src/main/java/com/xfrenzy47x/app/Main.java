package com.xfrenzy47x.app;

import java.util.Scanner;

public class Main {
    private static int counter = 0;
    private static final String WINS = " Wins";
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        char[] inputArray = new char[] {'_', '_', '_', '_', '_', '_', '_', '_', '_'};

        buildBoard(inputArray);
        boolean gameFinished;

        do {
            inputArray = handleNewCoordinates(scanner, inputArray);

            int[] numberOfPlays = buildBoard(inputArray);

            gameFinished = isGameFinished(inputArray, numberOfPlays);
        } while (!gameFinished);

    }

    private static char[] handleNewCoordinates(Scanner scanner, char[] inputArray) {

        boolean continueLoop = true;
        String message = "";
        do {
            String newInput = getInput(scanner);
            int x = 0;
            int y = 0;
            try {
                String[] inputSplit = newInput.split(" ");
                x = Integer.parseInt(inputSplit[0]);
                y = Integer.parseInt(inputSplit[1]);
            } catch (Exception ex) {
                message = "You should enter numbers!";
            }

            if ((x > 3 || y > 3) && message.isEmpty()) {
                message = "Coordinates should be from 1 to 3!";
            }

            int index = getIndex(x, y);
            if (inputArray[index] != '_' && inputArray[index] != ' ' && message.isEmpty()) {
                message = "This cell is occupied! Choose another one!";
            }

            if (!message.isEmpty()) {
                System.out.println(message);
                message = "";
                continue;
            }

            inputArray[index] = counter % 2 == 0 ? 'X' : 'O';
            counter++;
            continueLoop = false;
        } while (continueLoop);

        return inputArray;
    }

    private static int getIndex(int x, int y) {
        switch (x) {
            case 1:
                switch (y) {
                    case 1: return 0;
                    case 2: return 1;
                    case 3: return 2;
                }
                return 0;
            case 2:
                switch (y) {
                    case 1: return 3;
                    case 2: return 4;
                    case 3: return 5;
                }
                return 3;
            case 3:
                switch (y) {
                    case 1: return 6;
                    case 2: return 7;
                    case 3: return 8;
                }
                return 6;
            default:
                return 0;
        }
    }

    private static String getInput(Scanner scanner) {
        System.out.print("Enter the coordinates: ");
        return scanner.nextLine();
    }

    private static int[] buildBoard(char[] input) {
        System.out.println("---------");
        int x = 0;
        int y = 0;
        for (int i = 0; i < input.length; i++) {
            if (i == 0 || i == 3 || i == 6) {
                System.out.print("| ");
            }

            x = input[i] == 'X' || input[i] == 'x'? x + 1 : x;
            y = input[i] == 'O' || input[i] == 'o' ? y + 1 : y;

            System.out.print(input[i] + " ");

            if (i == 2 || i == 5 || i == 8) {
                System.out.println("|");
            }
        }
        System.out.println("---------");
        return new int[] {x, y};
    }

    private static boolean isGameFinished(char[] input, int[] numberOfPlays) {
        boolean gameNotFinished = gameNotFinished(input);
        if (impossiblePlay(numberOfPlays) && !gameNotFinished) {
            System.out.println("Impossible");
            return true;
        }
        if (checkForWinner(input)) { return true; }

        if (gameNotFinished(input)) {
            return false;
        }

        System.out.println("Draw");
        return true;
    }

    private static boolean gameNotFinished(char[] input) {
        for (char c : input) {
            if (c != 'X' && c != 'O') {
                return true;
            }
        }
        return false;
    }

    private static boolean impossiblePlay(int[] numberOfPlays) {
        int diff = numberOfPlays[0] - numberOfPlays[1];
        diff = diff < 0 ? diff * -1 : diff;
        return diff > 1;
    }

    private static boolean checkForWinner(char[] input) {
        String message = "";
        int counter = 0;

        // ROWS

        if (isSame(input[0], input[1], input[2])) {
            message = input[0] + WINS;
            counter++;
        }

        if (isSame(input[3], input[4], input[5])) {
            message = input[3] + WINS;
            counter++;
        }

        if (isSame(input[6], input[7], input[8])) {
            message = input[6] + WINS;
            counter++;
        }

        // COLUMNS

        if (isSame(input[0], input[3], input[6])) {
            message = input[0] + WINS;
            counter++;
        }

        if (isSame(input[1], input[4], input[7])) {
            message = input[1] + WINS;
            counter++;
        }

        if (isSame(input[2], input[5], input[8])) {
            message = input[2] + WINS;
            counter++;
        }

        // DIAGONAL

        if (isSame(input[0], input[4], input[8])) {
            message = input[0] + WINS;
            counter++;
        }

        if (isSame(input[2], input[4], input[6])) {
            message = input[2] + WINS;
            counter++;
        }

        if (counter > 1) {
            System.out.println("Impossible");
        } else if (!message.isEmpty()) {
            System.out.println(message);
        }
        return counter > 1 || !message.isEmpty();
    }

    private static boolean isSame(char input1, char input2, char input3) {
        return input1 == input2 && input2 == input3 && input1 != '_';
    }
}
