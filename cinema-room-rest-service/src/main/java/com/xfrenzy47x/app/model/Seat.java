package com.xfrenzy47x.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Seat {
    private int row;
    private int column;
    private int price;

    @JsonIgnore
    private boolean purchased;

    @JsonIgnore
    private String token;

    public Seat(int row, int column) {
        this.row = row;
        this.column = column;

        this.price = row <= 4 ? 10 : 8;
        this.purchased = false;
        this.token = "";
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @JsonIgnore
    public boolean isPurchased() {
        return purchased;
    }

    @JsonIgnore
    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }

    @JsonIgnore
    public String getToken() {
        return token;
    }

    @JsonIgnore
    public void setToken(String token) {
        this.token = token;
    }
}
