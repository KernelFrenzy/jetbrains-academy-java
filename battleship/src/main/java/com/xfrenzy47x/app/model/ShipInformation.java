package com.xfrenzy47x.app.model;

public class ShipInformation {
    String name;
    Integer health;

    public ShipInformation(String name, Integer health) {
        this.name = name;
        this.health = health;
    }

    @Override
    public String toString() {
        return name + " (" + health + " cells):";
    }
}
