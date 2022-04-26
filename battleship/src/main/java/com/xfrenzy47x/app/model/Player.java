package com.xfrenzy47x.app.model;

import com.xfrenzy47x.app.Helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Player {
    String state;
    Board board;
    List<Ship> ships;
    Integer playerNumber;

    public Player(String state, Integer playerNumber, Scanner scanner) {
        this.state = state;
        this.playerNumber = playerNumber;
        board = new Board();
        board.fogOfWarDisabled = true;
        ships = new ArrayList<>();
        buildShips(scanner);
        board.fogOfWarDisabled = false;
    }

    void switchState() {
        this.state = this.state.equals("ATK") ? "DEF" : "ATK";
    }

    public boolean hasShipsAlive() {
        return ships.stream().anyMatch(x -> x.getRemainingHealth() > 0);
    }

    void buildShips(Scanner scanner) {
        List<ShipInformation> shipInformationList = Helper.buildShipInformationList();
        System.out.println("Player " + playerNumber + ", place your ships on the game field");
        board.printBoard();
        for (ShipInformation info : shipInformationList) {
            Ship ship = new Ship(info);
            System.out.println("Enter the coordinates of the " + info);
            String combinedCoordinates = scanner.nextLine();

            do {
                ship = board.tryAddShip(combinedCoordinates, ship);
                if (ship.cells == null) {
                    combinedCoordinates = scanner.nextLine();
                }
            } while (ship.cells == null);

            ships.add(ship);
            board.printBoard();
        }
    }

    public Board getBoard() {
        return board;
    }
}
