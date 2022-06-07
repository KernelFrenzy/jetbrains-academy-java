package com.xfrenzy47x.app.services;

import com.xfrenzy47x.app.db.CarDatabaseService;
import com.xfrenzy47x.app.db.CompanyDatabaseService;
import com.xfrenzy47x.app.db.Database;
import com.xfrenzy47x.app.models.Car;
import com.xfrenzy47x.app.models.Company;

import java.util.List;
import java.util.Scanner;

public class ManagerService {
    private Database db;
    private Scanner scanner;

    public ManagerService (Database db, Scanner scanner) {
        this.db = db;
        this.scanner = scanner;
    }

    public void run() {
        boolean exit = false;
        while (!exit) {
            printManagerMenu();
            switch (scanner.nextLine()) {
                case "1":
                    listCompanies();
                    break;
                case "2":
                    createCompany();
                    break;
                case "0":
                default:
                    exit = true;
                    break;
            }
        }
    }

    private void listCompanies() {
        CompanyDatabaseService companyDatabaseService = new CompanyDatabaseService(db, scanner);
        List<Company> companies = companyDatabaseService.list();
        if (companies.isEmpty()) {
            System.out.println("The company list is empty!");
            return;
        }
        System.out.println();
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

        handleCarOptions(company);
    }

    private void handleCarOptions(Company company) {
        System.out.println();
        System.out.println(String.format("'%s' company", company.name));
        CarDatabaseService service = new CarDatabaseService(db, scanner);
        boolean exit = false;
        while (!exit) {
            System.out.println("1. Car list\n" +
                    "2. Create a car\n" +
                    "0. Back");
            switch (scanner.nextLine()) {
                case "1":
                    listCars(service, company);
                    break;
                case "2":
                    service.create(company);
                    break;
                case "0":
                    exit = true;
                    break;
            }
            System.out.println();
        }
    }

    private void listCars(CarDatabaseService service, Company company) {
        List<Car> cars = service.list(company);
        if (cars.isEmpty()) {
            System.out.println("The car list is empty!");
            return;
        }

        System.out.println("Car list: ");
        for (int i = 0; i < cars.size(); i++) {
            System.out.println((i+1) + ". " + cars.get(i));
        }
    }

    private void createCompany() {
        CompanyDatabaseService companyDatabaseService = new CompanyDatabaseService(db, scanner);
        companyDatabaseService.create();
    }

    private void printManagerMenu() {
        System.out.println();
        System.out.println("1. Company list\n" +
                "2. Create a company\n" +
                "0. Back");
    }
}
