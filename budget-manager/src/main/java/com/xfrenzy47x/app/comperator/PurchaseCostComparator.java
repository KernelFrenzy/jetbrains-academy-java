package com.xfrenzy47x.app.comperator;

import com.xfrenzy47x.app.model.Purchase;

import java.util.Comparator;

public class PurchaseCostComparator implements Comparator<Purchase> {

    @Override
    public int compare(Purchase o1, Purchase o2) {
        return Double.compare(o2.getCost(), o1.getCost());
    }
}
