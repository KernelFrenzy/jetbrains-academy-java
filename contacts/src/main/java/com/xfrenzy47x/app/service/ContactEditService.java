package com.xfrenzy47x.app.service;

import com.xfrenzy47x.app.model.Company;
import com.xfrenzy47x.app.model.Contact;
import com.xfrenzy47x.app.model.Person;
import com.xfrenzy47x.app.util.Validator;

import java.util.Scanner;

public class ContactEditService {
    private ContactEditService() {
        throw new IllegalStateException("Utility class");
    }
    public static Contact edit(Contact contact, Scanner scanner) {
        contact.displayFields();
        String field = scanner.nextLine().toLowerCase();
        System.out.println("Enter " + field);
        String fieldEdit = scanner.nextLine();

        if (contact instanceof Person) {
            editPerson(contact, field, fieldEdit);
        } else if (contact instanceof Company) {
            editCompany(contact, field, fieldEdit);
        }

        return contact;
    }

    private static Contact editPerson(Contact contact, String field, String fieldEdit) {
        Person person = (Person) contact;

        if (field.equalsIgnoreCase("number")) {
            person.setNumber(Validator.validNumber(fieldEdit));
        } else if (field.equalsIgnoreCase("name")){
            person.setName(fieldEdit);
        } else if (field.equalsIgnoreCase("surname")) {
            person.setSurname(fieldEdit);
        } else if (field.equalsIgnoreCase("birth")) {
            person.setBirthDate(Validator.validBirthDate(fieldEdit));
        } else if (field.equalsIgnoreCase("gender")) {
            person.setGender(Validator.validGender(fieldEdit));
        }

        return person;
    }

    private static Contact editCompany(Contact contact, String field, String fieldEdit) {
        Company company = (Company) contact;

        if (field.equalsIgnoreCase("number")) {
            company.setNumber(Validator.validNumber(fieldEdit));
        } else if (field.equalsIgnoreCase("organization name")){
            company.setName(fieldEdit);
        } else if (field.equalsIgnoreCase("address")) {
            company.setAddress(fieldEdit);
        }

        return company;
    }
}

