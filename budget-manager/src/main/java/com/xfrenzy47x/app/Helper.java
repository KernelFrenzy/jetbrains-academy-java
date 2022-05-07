package com.xfrenzy47x.app;

import com.xfrenzy47x.app.enums.PurchaseCategory;

public class Helper {

    private Helper() {
        throw new IllegalStateException("Helper class");
    }
    public static PurchaseCategory getPurchaseCategory(int value) {
        return switch (value) {
            case 1 -> PurchaseCategory.FOOD;
            case 2 -> PurchaseCategory.CLOTHES;
            case 3 -> PurchaseCategory.ENTERTAINMENT;
            case 4 -> PurchaseCategory.OTHER;
            case 5 -> PurchaseCategory.ALL;
            default -> null;
        };
    }

    public static void how() {
        System.out.println("How?");
    }
}
