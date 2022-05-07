package com.xfrenzy47x.app.model;

import com.xfrenzy47x.app.Helper;
import com.xfrenzy47x.app.enums.PurchaseCategory;
import com.xfrenzy47x.app.helper.ScannerReader;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Bank extends ScannerReader {
    private double income;
    private List<Purchase> purchases;
    public Bank() {
        income = 0;
        purchases = new ArrayList<>();
    }

    public void printMenu() {
        System.out.println("Choose your action:");
        System.out.println("1) Add income");
        System.out.println("2) Add purchase");
        System.out.println("3) Show list of purchases");
        System.out.println("4) Balance");
        System.out.println("5) Save");
        System.out.println("6) Load");
        System.out.println("7) Analyze (Sort)");
        System.out.println("0) Exit");
    }

    public int checkAction() {
        printMenu();
        int input = readInt();
        System.out.println();
        switch (input) {
            case 1 -> addIncome();
            case 2 -> addPurchase();
            case 3 -> showListOfPurchases();
            case 4 -> showBalance();
            case 5 -> savePurchases();
            case 6 -> loadPurchases();
            case 7 -> analyzePurchases();
            case 0 -> System.out.println("Bye!");
            default -> Helper.how();
        }
        return input;
    }

    private void printAnalyzeOptions() {
        System.out.println("How do you want to sort?");
        System.out.println("1) Sort all purchases");
        System.out.println("2) Sort by type");
        System.out.println("3) Sort certain type");
        System.out.println("4) Back");
    }

    private void analyzePurchases() {
        do {
            printAnalyzeOptions();
            int option = readInt();
            System.out.println();
            if (option >= 4 || option <= 0) return;
            Analyzer analyzer = new Analyzer(purchases, option);
            analyzer.printSortedResult();
            System.out.println();
        } while (true);
    }

    private void savePurchases() {
        File purchaseFile = initializeFile();

        try (PrintWriter printWriter = new PrintWriter(purchaseFile)) {
            for (Purchase purchase : purchases) {
                printWriter.println(purchase.getSavedFormat());
            }
            printWriter.println("Income," + income);
        } catch (IOException ex) {
            System.out.printf("An exception occurred %s", ex.getMessage());
        }
        System.out.println("Purchases were saved!");
        System.out.println();
    }

    private File initializeFile() {
        File file = new File("purchases.txt");
        if (!file.exists()) {
            try {
                boolean created = file.createNewFile();
                if (!created) {
                    System.out.println("File didn't exist, but was wasn't created because it exists :o?");
                }
            } catch (IOException ex) {
                System.out.printf("An io exception occurred %s", ex.getMessage());
            }
        }
        return file;
    }

    private void loadPurchases() {
        purchases = new ArrayList<>();
        File purchaseFile = initializeFile();

        try (Scanner scanner = new Scanner(purchaseFile)) {
            while (scanner.hasNextLine()) {
                String[] items = scanner.nextLine().split(",");
                if (items.length == 2) {
                    income = Double.parseDouble(items[1]);
                } else {
                    purchases.add(new Purchase(items[1], Double.parseDouble(items[2]), Helper.getPurchaseCategory(Integer.parseInt(items[0]))));
                }

            }
        } catch (IOException ex) {
            System.out.printf("An exception occurred %s", ex.getMessage());
        }

        System.out.println("Purchases were loaded!");
        System.out.println();
    }

    private void addIncome() {
        System.out.println("Enter income: ");
        income += readDouble();
        System.out.println("Income was added!");
        System.out.println();
    }

    private void printPurchaseCategoriesAddPurchase() {
        System.out.println("Choose the type of purchase");
        System.out.println("1) Food");
        System.out.println("2) Clothes");
        System.out.println("3) Entertainment");
        System.out.println("4) Other");
        System.out.println("5) Back");
    }

    private void printPurchaseCategoriesShowPrice() {
        System.out.println("Choose the type of purchase");
        System.out.println("1) Food");
        System.out.println("2) Clothes");
        System.out.println("3) Entertainment");
        System.out.println("4) Other");
        System.out.println("5) All");
        System.out.println("6) Back");
    }

    private void addPurchase() {
        int category;
        do {
            printPurchaseCategoriesAddPurchase();
            category = readInt();
            System.out.println();
            if (category == 5 || category == 0) {
                return;
            }

            System.out.println("Enter purchase name:");
            String name = readString();
            System.out.println("Enter its price:");
            double cost = readDouble();

            purchases.add(new Purchase(name, cost, Helper.getPurchaseCategory(category)));
            System.out.println("Purchase was added!");
            System.out.println();
        } while (true);
    }

    private void showListOfPurchases() {
        do {
            printPurchaseCategoriesShowPrice();
            int category = readInt();
            System.out.println();
            if (category >= 6 || category == 0) {
                return;
            }
            PurchaseCategory purchaseCategory = Helper.getPurchaseCategory(category);
            List<Purchase> purchasesToView = purchases.stream()
                    .filter(x -> x.getPurchaseCategory().equals(purchaseCategory) || purchaseCategory == PurchaseCategory.ALL)
                    .toList();
            System.out.println(purchaseCategory.toString());
            if (purchasesToView.isEmpty()) {
                System.out.println("The purchase list is empty");
            } else {
                for (Purchase purchase : purchasesToView) {
                    System.out.println(purchase.toString());
                }
                System.out.println("Total sum: $" + getPurchaseSum(purchasesToView));
            }
            System.out.println();
        } while (true);
    }

    private double getPurchaseSum(List<Purchase> purchases) {
        return purchases.stream().mapToDouble(Purchase::getCost).sum();
    }

    private void showBalance() {
        System.out.print("Balance: $");
        System.out.println(income - getPurchaseSum(purchases));
        System.out.println();
    }
}