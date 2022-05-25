package com.xfrenzy47x.app.models;

public class Stat {
    private String name;
    private Double value;

    public Stat(String name, Double value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Double getValue() {
        return value;
    }
}