package com.xfrenzy47x.app.model;

import java.util.List;

public class Ship extends ShipInformation {
    List<Cell> cells;

    public Integer getRemainingHealth() {
        Long dmg = cells.stream().filter(x -> x.publicValue == "X").count();
        return Math.toIntExact(health - dmg);
    }

    public Ship(ShipInformation shipInformation) {
        super(shipInformation.name, shipInformation.health);
        cells = null;
    }

    public List<Cell> getCells() {
        return cells;
    }
}
