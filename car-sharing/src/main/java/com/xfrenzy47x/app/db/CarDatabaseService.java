package com.xfrenzy47x.app.db;

import com.xfrenzy47x.app.models.Car;
import com.xfrenzy47x.app.models.Company;

import java.util.List;
import java.util.Scanner;

public class CarDatabaseService {
    private final Database db;
    private final Scanner scanner;

    public CarDatabaseService(Database db, Scanner scanner) {
        this.db = db;
        this.scanner = scanner;
    }

    public List<Car> list(Company company) {
        return db.readCars(company.id, false);
    }

    public List<Car> available(Company company) {
        return db.readCars(company.id, true);
    }

    public Car get(int id) {
        return db.readCar(id);
    }

    public void create(Company company) {
        System.out.println();
        System.out.println("Enter the car name: ");
        db.insertIntoCar(scanner.nextLine(), company.id);
    }
}
