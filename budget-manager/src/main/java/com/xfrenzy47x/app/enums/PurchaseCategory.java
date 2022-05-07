package com.xfrenzy47x.app.enums;

public enum PurchaseCategory {
    FOOD("Food", 1),
    CLOTHES("Clothes", 2),
    ENTERTAINMENT("Entertainment", 3),
    OTHER("Other", 4),
    ALL("All", 5);

    private final String name;
    private final int value;
    PurchaseCategory(String name, int value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return this.name + ":";
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }
}
