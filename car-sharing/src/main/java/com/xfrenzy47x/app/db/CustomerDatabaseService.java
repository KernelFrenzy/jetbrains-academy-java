package com.xfrenzy47x.app.db;

import com.xfrenzy47x.app.models.Customer;

import java.util.List;
import java.util.Scanner;

public class CustomerDatabaseService {
    private final Database db;
    private final Scanner scanner;

    public CustomerDatabaseService(Database db, Scanner scanner) {
        this.db = db;
        this.scanner = scanner;
    }

    public List<Customer> list() {
        return db.readCustomers();
    }

    public void create() {
        System.out.println();
        System.out.println("Enter the customer name: ");
        db.insertIntoCustomer(scanner.nextLine());
    }

    public Customer get(int customerId) {
        return list().stream().filter(x -> x.id == customerId).findFirst().orElse(null);
    }

    public boolean update(int carId, int customerId) {
        return db.updateCustomer(carId, customerId);
    }
}

