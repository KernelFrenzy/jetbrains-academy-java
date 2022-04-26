package com.xfrenzy47x.app.model;

import com.xfrenzy47x.app.exception.ErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Board {
    List<Row> rows;
    boolean fogOfWarDisabled;

    public Board() {
        rows = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            rows.add(new Row(i));
        }
    }

    public String attack(char rowLetter, int colNumber) {
        Row r = rows.stream().filter(x -> x.rowLetter.equals(rowLetter)).findFirst().get();
        Cell c = r.cells.stream().filter(x -> x.colNumber.equals(colNumber)).findFirst().get();

        if (c.ship == null) {
            c.publicValue = "M";
            c.privateValue = "M";
        } else {
            c.publicValue = "X";
            c.privateValue = "X";
            Cell shipCell = c.ship.cells.stream().filter(x -> x.equals(c)).findFirst().get();
            shipCell.publicValue = "X";
            shipCell.privateValue = "X";
        }

        printBoard();
        String message = "You missed! Try again:";
        if (c.publicValue == "X") {
            message = c.ship.getRemainingHealth() > 0 ? "You hit a ship! Try again:" : "You sank a ship! Specify a new target:";
        }
        return message;
    }

    boolean placeShip = false;

    public Ship tryAddShip(String input, Ship theShip) {
        String[] coordinates = input.split(" ");
        theShip.cells = null;
        placeShip = true;

        List<Cell> coordinateCells = null;
        try {
            coordinateCells = tryGetCoordinateCells(coordinates);
        } catch (ErrorException ex) {
            System.out.println(ex.getMessage());
            placeShip = false;
        }

        if (placeShip) {
            if (coordinateCells.size() != theShip.health) {
                System.out.println("Error! Wrong length of the " + theShip.name + "! Try again:");
                placeShip = false;
            } else {
                try {
                    isColidingOrTooClose(coordinateCells);
                } catch (ErrorException ex) {
                    System.out.println(ex.getMessage());
                    placeShip = false;

                }
            }

            if (placeShip) {
                theShip.cells = new ArrayList<>();
                placeShip(coordinateCells, theShip);
            }
        }

        return theShip;
    }

    private Ship placeShip(List<Cell> coordinates, Ship theShip) {
        theShip.cells = new ArrayList<>();
        for (Cell coordinate : coordinates) {
            Row row = this.rows.stream().filter(x -> x.rowLetter.equals(coordinate.rowLetter)).findFirst().get();
            Cell cell = row.cells.stream().filter(x -> x.toString().equals(coordinate.toString())).findFirst().get();

            cell.privateValue = "O";
            theShip.cells.add(cell);
            cell.ship = theShip;
        }
        return theShip;
    }

    private void isColidingOrTooClose(List<Cell> coordinates) throws ErrorException {
        for (Cell coordinate : coordinates) {
            Optional<Row> r = this.rows.stream().filter(x -> x.rowLetter.equals(coordinate.rowLetter)).findFirst();
            if (r.isEmpty()) {
                throw new ErrorException("Error! Row letter '" + coordinate.rowLetter + "' does not exist. Must be between A and J inclusive. Try again:");
            } else {
                Row row = r.get();
                Optional<Cell> c = row.cells.stream().filter(x -> x.toString().equals(coordinate.toString())).findFirst();
                if (c.isEmpty()) {
                    throw new ErrorException("Error! Column number '" + coordinate.rowLetter + "' does not exist. Must be between 1 and 10 inclusive. Try again:");
                } else {
                    Cell cell = c.get();
                    if (cell.notEmpty()) {
                        throw new ErrorException("Error! Wrong ship location! Try again:");
                    } else {
                        checkAroundCell(row, cell, coordinates);
                    }
                }
            }
        }
    }

    private void checkAroundCell(Row row, Cell cell, List<Cell> coordinates) throws ErrorException {
        Cell leftCell = cell.colNumber != 0 ? getHorizontalCell(cell.colNumber-1, row) : null;
        cellClash(leftCell, coordinates);

        Cell rightCell = cell.colNumber != 10 ? getHorizontalCell(cell.colNumber+1, row) : null;
        cellClash(rightCell, coordinates);

        Cell topCell = cell.rowLetter != 'A' ? getVerticalCell((char) (cell.rowLetter - 1), cell.colNumber) : null;
        cellClash(topCell, coordinates);

        Cell bottomCell = cell.rowLetter != 'J' ? getVerticalCell((char) (cell.rowLetter + 1), cell.colNumber) : null;
        cellClash(bottomCell, coordinates);
    }

    private Cell getHorizontalCell(Integer colNumber, Row row) {
        return row.cells.stream().filter(x -> x.colNumber == colNumber).findFirst().orElse(null);
    }

    private Cell getVerticalCell(Character rowLetter, Integer colNumber) {
        Optional<Row> r = this.rows.stream().filter(x -> x.rowLetter.equals(rowLetter)).findFirst();
        if (r.isEmpty()) return null;
        Row row = r.get();
        return row.cells.stream().filter(x -> x.colNumber == colNumber).findFirst().orElse(null);
    }

    private boolean cellClash(Cell cell, List<Cell> gracedCoordinates) throws ErrorException {
        if (cell != null && gracedCoordinates.stream().noneMatch(x -> x.toString().equals(cell.toString())) && cell.notEmpty()) {
            throw new ErrorException("Error! You placed it too close to another one. Try again:");
        }
        return false;
    }

    private List<Cell> tryGetCoordinateCells(String[] coordinates) throws ErrorException {
        List<Cell> cells = new ArrayList<>();

        Cell firstCoordinate = new Cell(coordinates[0].charAt(0), Integer.parseInt(coordinates[0].substring(1)));
        Cell lastCoordinate = new Cell(coordinates[1].charAt(0), Integer.parseInt(coordinates[1].substring(1)));

        if (firstCoordinate.rowLetter != lastCoordinate.rowLetter && firstCoordinate.colNumber != lastCoordinate.colNumber) {
            throw new ErrorException("Error! Wrong ship location! Try again:");
        }

        if (Objects.equals(firstCoordinate.rowLetter, lastCoordinate.rowLetter)) {
            //Going Left/Right
            populateHorizontalCells(firstCoordinate, lastCoordinate, cells);

        } else {
            //Going Up/Down
            populateVerticalCells(firstCoordinate, lastCoordinate, cells);
        }

        return cells;
    }

    private List<Cell> populateVerticalCells(Cell firstCoordinate, Cell lastCoordinate, List<Cell> cells) {
        if (firstCoordinate.rowLetter < lastCoordinate.rowLetter) {
            cells.add(firstCoordinate);
            int nextVal = firstCoordinate.rowLetter + 1;
            for (int i = nextVal; i < lastCoordinate.rowLetter; i++) {
                cells.add(new Cell((char) i, firstCoordinate.colNumber));
            }
            cells.add(lastCoordinate);
        } else {
            cells.add(lastCoordinate);
            int nextVal = lastCoordinate.rowLetter + 1;
            for (int i = nextVal; i < firstCoordinate.rowLetter; i++) {
                cells.add(new Cell((char) i, lastCoordinate.colNumber));
            }
            cells.add(firstCoordinate);
        }

        return cells;
    }

    private List<Cell> populateHorizontalCells(Cell firstCoordinate, Cell lastCoordinate, List<Cell> cells) {
        if (firstCoordinate.colNumber < lastCoordinate.colNumber) {
            cells.add(firstCoordinate);
            int nextVal = firstCoordinate.colNumber+1;
            for (int i = nextVal; i < lastCoordinate.colNumber; i++) {
                cells.add(new Cell(firstCoordinate.rowLetter, i));
            }
            cells.add(lastCoordinate);
        } else {
            cells.add(lastCoordinate);
            int nextVal = lastCoordinate.colNumber+1;
            for (int i = nextVal; i < firstCoordinate.colNumber; i++) {
                cells.add(new Cell(lastCoordinate.rowLetter, i));
            }
            cells.add(firstCoordinate);
        }
        return cells;
    }

    public void printPublicBoard() {
        fogOfWarDisabled = true;
        printBoard();
        fogOfWarDisabled = false;
    }

    public void printBoard() {
        printFirstLine();
        for (int i = 0; i < rows.size(); i++) {
            Row r = rows.get(i);
            System.out.print(r.rowLetter + " ");
            for (int j = 0; j < r.cells.size(); j++) {
                System.out.print((fogOfWarDisabled ? r.cells.get(j).privateValue : r.cells.get(j).publicValue) + " ");
            }
            System.out.print("\n");
        }
    }

    private void printFirstLine() {
        for (int i = 0; i <= 10; i++) {
            if (i == 0) {
                System.out.print("  ");
            } else {
                System.out.print(i + " ");
            }
        }
        System.out.print("\n");
    }
}
