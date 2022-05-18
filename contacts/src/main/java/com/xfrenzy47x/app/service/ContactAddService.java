package com.xfrenzy47x.app.service;

import com.xfrenzy47x.app.model.Company;
import com.xfrenzy47x.app.model.Contact;
import com.xfrenzy47x.app.model.Person;
import com.xfrenzy47x.app.util.Validator;

import java.time.LocalDate;
import java.util.Scanner;

public class ContactAddService {
    private ContactAddService() {
        throw new IllegalStateException("Utility class");
    }
    public static Contact add(Scanner scanner) {
        Contact newContact = null;
        System.out.println("Enter the type (person, organization): ");
        String type = scanner.nextLine();
        if (type.equalsIgnoreCase("person")) {
            newContact = addPerson(scanner);
        } else if (type.equalsIgnoreCase("organization")) {
            newContact = addCompany(scanner);
        }

        return newContact;
    }

    private static Contact addPerson(Scanner scanner) {
        System.out.println("Enter the name: ");
        String name = scanner.nextLine();
        System.out.println("Enter the surname: ");
        String surname = scanner.nextLine();
        System.out.println("Enter the birth date: ");
        LocalDate birthDate = Validator.validBirthDate(scanner.nextLine());
        System.out.println("Enter the gender (M, F): ");
        Character gender = Validator.validGender(scanner.nextLine());
        System.out.println("Enter the number: ");
        String number = Validator.validNumber(scanner.nextLine());

        return (new Person(name, surname, birthDate, gender, number));
    }

    private static Contact addCompany(Scanner scanner) {
        System.out.println("Enter the organization name: ");
        String name = scanner.nextLine();
        System.out.println("Enter the address: ");
        String address = scanner.nextLine();
        System.out.println("Enter the number: ");
        String number = Validator.validNumber(scanner.nextLine());

        return (new Company(name, address, number));
    }
}
