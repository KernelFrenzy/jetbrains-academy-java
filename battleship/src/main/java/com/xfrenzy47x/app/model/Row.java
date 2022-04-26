package com.xfrenzy47x.app.model;

import com.xfrenzy47x.app.Helper;

import java.util.ArrayList;
import java.util.List;


public class Row {
    Character rowLetter;
    List<Cell> cells;

    public Row(Integer rowNumber) {
        cells = new ArrayList<>();
        rowLetter = Helper.LETTERS[rowNumber];
        for (int j = 1; j <= 10; j++) {
            cells.add(new Cell(rowLetter, j));
        }
    }

    public Character getRowLetter() {
        return rowLetter;
    }

    public void setRowLetter(Character rowLetter) {
        this.rowLetter = rowLetter;
    }

    public List<Cell> getCells() {
        return cells;
    }

    public void setCells(List<Cell> cells) {
        this.cells = cells;
    }
}
