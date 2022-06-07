package com.xfrenzy47x.app.models;

public class Car {
    public int id;
    public String name;
    public int companyId;

    public Car(int id, String name, int companyId) {
        this.id = id;
        this.name = name;
        this.companyId = companyId;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
