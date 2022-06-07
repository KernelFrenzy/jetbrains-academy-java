package com.xfrenzy47x.app.db;

import com.xfrenzy47x.app.models.Company;

import java.util.List;
import java.util.Scanner;

public class CompanyDatabaseService {
    private final Database db;
    private final Scanner scanner;

    public CompanyDatabaseService(Database db, Scanner scanner) {
        this.db = db;
        this.scanner = scanner;
    }

    public List<Company> list() {
        return db.readCompanies();
    }

    public Company get(int companyId) {
        return db.readCompanies().stream().filter(x -> x.id == companyId).findFirst().orElse(null);
    }

    public void create() {
        System.out.println();
        System.out.println("Enter the company name: ");
        db.insertIntoCompany(scanner.nextLine());
    }
}
