package com.xfrenzy47x.app.services;

import com.xfrenzy47x.app.db.CustomerDatabaseService;
import com.xfrenzy47x.app.db.Database;

import java.util.Scanner;

public class MainService {
    private final Database db;
    private final Scanner scanner;
    public MainService(Database db, Scanner scanner) {
        this.db = db;
        this.scanner = scanner;
    }

    public void run() {
        boolean exit = false;
        while (!exit) {
            printInitMenu();
            switch (scanner.nextLine()) {
                case "1":
                    loginAsManager();
                    break;
                case "2":
                    loginAsCustomer();
                    break;
                case "3":
                    createCustomer();
                    break;
                case "0":
                default:
                    exit = true;
                    break;
            }
            System.out.println();
        }
    }

    private void loginAsCustomer() {
        CustomerService customerService = new CustomerService(db, scanner);
        customerService.run();
    }

    private void createCustomer() {
        CustomerDatabaseService customerDatabaseService = new CustomerDatabaseService(db, scanner);
        customerDatabaseService.create();
        System.out.println();
    }

    private void loginAsManager() {
        ManagerService managerService = new ManagerService(db, scanner);
        managerService.run();
    }

    private void printInitMenu() {
        System.out.println("1. Log in as a manager:");
        System.out.println("2. Log in as a customer:");
        System.out.println("3. Create a customer: ");
        System.out.println("0. Exit");
    }
}
