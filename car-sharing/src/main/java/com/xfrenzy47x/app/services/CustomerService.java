package com.xfrenzy47x.app.services;

import com.xfrenzy47x.app.db.CarDatabaseService;
import com.xfrenzy47x.app.db.CompanyDatabaseService;
import com.xfrenzy47x.app.db.CustomerDatabaseService;
import com.xfrenzy47x.app.db.Database;
import com.xfrenzy47x.app.models.Car;
import com.xfrenzy47x.app.models.Company;
import com.xfrenzy47x.app.models.Customer;

import java.util.List;
import java.util.Scanner;

public class CustomerService {
    private final Database db;
    private final Scanner scanner;

    public CustomerService(Database db, Scanner scanner) {
        this.db = db;
        this.scanner = scanner;
    }

    public void run() {
        CustomerDatabaseService customerDatabaseService = new CustomerDatabaseService(db, scanner);
        List<Customer> customers = customerDatabaseService.list();

        if (customers.isEmpty()) {
            System.out.println("The customer list is empty!");
            return;
        }

        System.out.println("Choose a customer: ");
        for (int i = 0; i < customers.size(); i++) {
            System.out.println((i+1) + ". " + customers.get(i));
        }
        System.out.println("0. Back");

        String action = scanner.nextLine();
        if (action.equals("0")) {
            return;
        }

        Customer customer = customers.get(Integer.parseInt(action)-1);
        if (customer == null) return;

        handleCustomerOptions(customer);
    }

    private void handleCustomerOptions(Customer customer) {
        boolean exit = false;
        while (!exit) {
            printCustomerMenu();
            switch (scanner.nextLine()) {
                case "1":
                    rentCar(customer);
                    break;
                case "2":
                    returnCar(customer);
                    break;
                case "3":
                    myCar(customer);
                    break;
                case "0":
                default:
                    exit = true;
                    break;
            }
            if (!exit) {
                CustomerDatabaseService service = new CustomerDatabaseService(db, scanner);
                customer = service.get(customer.id);
            }
        }
    }

    private void rentCar(Customer customer) {
        if (customer.rentedCarId != null) {
            System.out.println("You've already rented a car!");
            return;
        }
        CompanyDatabaseService databaseService = new CompanyDatabaseService(db, scanner);
        List<Company> companies = databaseService.list();

        if (companies.isEmpty()) {
            System.out.println("The company list is empty!");
            return;
        }

        while (true) {
            System.out.println("Choose a company: ");
            for (int i = 0; i < companies.size(); i++) {
                System.out.println((i+1) + ". " + companies.get(i));
            }
            System.out.println("0. Back");

            String action = scanner.nextLine();
            if (action.equals("0")) {
                return;
            }

            Company company = companies.get(Integer.parseInt(action)-1);
            if (company == null) return;

            CarDatabaseService carDatabaseService = new CarDatabaseService(db, scanner);
            List<Car> availableCars = carDatabaseService.available(company);
            if (availableCars.isEmpty()) {
                System.out.println("No available cars in the '" + company.name + "' company.");
            } else {
                System.out.println("Choose a car: ");
                for (int i = 0; i < availableCars.size(); i++) {
                    System.out.println((i+1) + ". " + availableCars.get(i));
                }
                System.out.println("0. Back");
                action = scanner.nextLine();
                if (!action.equals("0")) {
                    Car car = availableCars.get(Integer.parseInt(action) - 1);
                    if (car != null && db.updateCustomer(car.id, customer.id)) {
                        System.out.println("You rented '" + car.name + "'");
                        return;
                    }
                }
            }
        }
    }

    private void returnCar(Customer customer) {
        if (customer.rentedCarId == null) {
            noCar();
            return;
        }

        if (db.updateCustomer(-1, customer.id)) {
            System.out.println("You've returned a rented car!");
        }
    }

    private void myCar(Customer customer) {
        if (customer.rentedCarId == null) {
            noCar();
            return;
        }

        CarDatabaseService carDatabaseService = new CarDatabaseService(db, scanner);
        Car car = carDatabaseService.get(customer.rentedCarId);

        if (car == null) return;

        CompanyDatabaseService companyDatabaseService = new CompanyDatabaseService(db, scanner);
        Company company = companyDatabaseService.get(car.companyId);

        if (company == null) return;

        System.out.println("Your rented car: ");
        System.out.println(car);
        System.out.println("Company:");
        System.out.println(company);


    }

    private void noCar() {
        System.out.println("You didn't rent a car!");
    }

    private void printCustomerMenu() {
        System.out.println();
        System.out.println("1. Rent a car\n" +
                "2. Return a rented car\n" +
                "3. My rented car\n" +
                "0. Back");
    }
}
