package com.xfrenzy47x.app.models;

public class Customer {
    public int id;
    public String name;
    public Integer rentedCarId;

    @Override
    public String toString() {
        return this.name;
    }
}
