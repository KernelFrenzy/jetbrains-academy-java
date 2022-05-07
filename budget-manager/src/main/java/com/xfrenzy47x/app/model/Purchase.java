package com.xfrenzy47x.app.model;

import com.xfrenzy47x.app.enums.PurchaseCategory;

public class Purchase {
    private String name;
    private double cost;
    private PurchaseCategory purchaseCategory;

    public Purchase(String name, double cost, PurchaseCategory purchaseCategory) {
        this.name = name;
        this.cost = cost;
        this.purchaseCategory = purchaseCategory;
    }
    @Override
    public String toString() {
        return name + " $" + String.format("%.2f",cost);
    }

    public double getCost() {
        return cost;
    }

    public String getName() {
        return name;
    }

    public PurchaseCategory getPurchaseCategory() {
        return purchaseCategory;
    }

    public String getSavedFormat() {
        return purchaseCategory.getName() + "," + name + "," + cost;
    }
}