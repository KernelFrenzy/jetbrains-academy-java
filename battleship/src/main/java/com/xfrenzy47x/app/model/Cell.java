package com.xfrenzy47x.app.model;

public class Cell {
    Character rowLetter;
    Integer colNumber;
    String privateValue;
    String publicValue;
    Ship ship;

    public Cell(Character rowLetter, Integer colNumber) {
        this.rowLetter = rowLetter;
        this.colNumber = colNumber;
        privateValue = "~";
        publicValue = "~";
        ship = null;
    }

    public boolean notEmpty() {
        return !privateValue.equals("~");
    }

    @Override
    public String toString() {
        return rowLetter.toString() + colNumber.toString();
    }
}
