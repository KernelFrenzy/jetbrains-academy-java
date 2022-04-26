package com.xfrenzy47x.app.model;

import com.xfrenzy47x.app.Helper;
import com.xfrenzy47x.app.Main;

import java.util.Arrays;
import java.util.Scanner;

public class Game {
    boolean isFinished;
    Player playerOne;
    Player playerTwo;

    public Game() {
        Scanner scanner = new Scanner(System.in);
        isFinished = false;
        playerOne = new Player("DEF", 1, scanner);
        Helper.passControl();
        playerTwo = new Player("ATK", 2, scanner);
        startGame(scanner);
    }

    public void printAttackingPlayerBoards() {
        getDefendingPlayer().board.printBoard();
        System.out.println("---------------------");
        getAttackingPlayer().board.printPublicBoard();
        System.out.println("Player " + getAttackingPlayer().playerNumber + ", it's your turn:");
    }

    public Player getAttackingPlayer() {
        return this.playerOne.state.equals("ATK") ? this.playerOne : this.playerTwo;
    }

    public Player getDefendingPlayer() {
        return this.playerOne.state.equals("DEF") ? this.playerOne : this.playerTwo;
    }

    public Board getDefendingPlayerBoard() {
        return getDefendingPlayer().getBoard();
    }

    public void switchPlayerStates() {
        playerOne.switchState();
        playerTwo.switchState();
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean value) {
        this.isFinished = value;
    }

    private void startGame(Scanner scanner) {
        do {
            Helper.passControl();
            this.switchPlayerStates();
            this.printAttackingPlayerBoards();

            boolean badInput;
            char rowLetter;
            int colNumber;

            do {
                badInput = false;
                String coordinate = scanner.nextLine();
                rowLetter = coordinate.charAt(0);
                colNumber = Integer.parseInt(coordinate.substring(1));

                char finalRowLetter = rowLetter;
                if (Arrays.stream(Helper.LETTERS).noneMatch(x -> x.equals(finalRowLetter)) || colNumber > 10 || colNumber < 1) {
                    System.out.println("Error! You entered the wrong coordinates! Try again:");
                    badInput = true;
                }
            } while (badInput);

            String message = this.getDefendingPlayerBoard().attack(rowLetter, colNumber);
            if (message.contains("sank") && !this.getDefendingPlayer().hasShipsAlive()) {
                this.setFinished(true);
                message = "You sank the last ship. You won. Congratulations!";
            }
            System.out.println(message);
        } while (!this.isFinished());
    }
}
