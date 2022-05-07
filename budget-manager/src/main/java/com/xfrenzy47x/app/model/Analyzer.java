package com.xfrenzy47x.app.model;

import com.xfrenzy47x.app.Helper;
import com.xfrenzy47x.app.comperator.PurchaseCostComparator;
import com.xfrenzy47x.app.enums.PurchaseCategory;
import com.xfrenzy47x.app.helper.ScannerReader;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Analyzer extends ScannerReader {
    List<Purchase> purchases;
    int option;
    public Analyzer(List<Purchase> purchases, int option) {
        this.purchases = purchases;
        this.option = option;
    }

    private void allSorting() {
        if (purchases.isEmpty()) {
            System.out.println("The purchase list is empty!\n");
            return;
        }
        purchases.sort(new PurchaseCostComparator());

        System.out.println("All:");
        for (Purchase purchase : purchases) {
            System.out.println(purchase.toString());
        }
        System.out.println("Total: " + String.format("%.2f",getTotalByType(PurchaseCategory.ALL)));
    }

    private double getTotalByType(PurchaseCategory purchaseCategory) {
        return purchases.stream()
                .filter(x -> x.getPurchaseCategory().equals(purchaseCategory) || purchaseCategory == PurchaseCategory.ALL)
                .mapToDouble(Purchase::getCost)
                .sum();
    }

    private void byTypesSorting() {
        List<Purchase> typedPurchases = new ArrayList<>();

        typedPurchases.add(new Purchase("Food", getTotalByType(PurchaseCategory.FOOD), PurchaseCategory.FOOD));
        typedPurchases.add(new Purchase("Entertainment", getTotalByType(PurchaseCategory.ENTERTAINMENT), PurchaseCategory.ENTERTAINMENT));
        typedPurchases.add(new Purchase("Clothes", getTotalByType(PurchaseCategory.CLOTHES), PurchaseCategory.CLOTHES));
        typedPurchases.add(new Purchase("Other", getTotalByType(PurchaseCategory.OTHER), PurchaseCategory.OTHER));

        typedPurchases.sort(new PurchaseCostComparator());
        System.out.println("Types:");
        for (Purchase purchase : typedPurchases) {
            System.out.println(purchase.getName() + " - $" + String.format("%.2f", purchase.getCost()));
        }
        purchases = typedPurchases;
        System.out.println("Total sum: $" + String.format("%.2f",getTotalByType(PurchaseCategory.ALL)));
    }

    private void printTypes() {
        System.out.println("Choose the type of purchase\n");
        System.out.println("1) Food\n");
        System.out.println("2) Clothes\n");
        System.out.println("3) Entertainment\n");
        System.out.println("4) Other\n");
    }

    private void byTypeSorting() {
        printTypes();
        int type = readInt();
        if (type > 4) return;

        PurchaseCategory category = Helper.getPurchaseCategory(type);
        System.out.println(category.toString());
        purchases = purchases.stream().filter(x -> x.getPurchaseCategory().equals(category)).collect(Collectors.toList());
        purchases.sort(new PurchaseCostComparator());

        if (purchases.isEmpty()) {
            System.out.println("The purchase list is empty!\n");
            return;
        }

        for (Purchase purchase : purchases) {
            System.out.println(purchase.toString());
        }
        System.out.println("Total sum: $" + String.format("%.2f",getTotalByType(category)));
    }

    public void printSortedResult() {
        switch (option) {
            case 1 -> allSorting();
            case 2 -> byTypesSorting();
            case 3 -> byTypeSorting();
            default -> Helper.how();
        }
    }
}