package com.xfrenzy47x.app.models;

public class Company {
    public int id;
    public String name;

    public Company(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
