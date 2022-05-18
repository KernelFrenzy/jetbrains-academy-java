package com.xfrenzy47x.app.service;

import com.xfrenzy47x.app.model.Contact;
import com.xfrenzy47x.app.util.Serialization;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ContactService {
    private List<Contact> contactList;
    private final Scanner scanner;

    private static final String PHONE_BOOK_PATH = "/home/xfrenzy47x/IdeaProjects/Contacts/Contacts/task/src/contacts/phonebook.data";

    public ContactService() {
        contactList = new ArrayList<>();
        scanner = new Scanner(System.in);
        Serialization.createFile(new File(getPhoneBookPath()));
    }

    public void loadContacts(List<Contact> contactList) {
        this.contactList = contactList;
    }

    public void saveContacts() {
        Serialization.serialize(this.contactList, getPhoneBookPath());
    }

    public String getPhoneBookPath() {
        return PHONE_BOOK_PATH;
    }

    public String displayMenu() {
        System.out.println("[menu] Enter action (add, list, search, count, exit): ");
        return scanner.nextLine();
    }

    private String displayListMenu() {
        System.out.println("[list] Enter action ([number], back): ");
        return scanner.nextLine();
    }

    private String displaySearchMenu() {
        System.out.println("[search] Enter action ([number], back, again):");
        return scanner.nextLine();
    }

    private String displayRecordMenu() {
        System.out.println("[record] Enter action (edit, delete, menu):");
        return scanner.nextLine();
    }

    public void addEntry() {
        Contact newContact = ContactAddService.add(scanner);
        if (newContact != null) {
            contactList.add(newContact);
            System.out.println("New record added!");
        }
    }

    public void listEntries() {
        ContactListService.list(contactList);
        String action = displayListMenu();
        if (action.equalsIgnoreCase("back")) return;
        int number = Integer.parseInt(action) -1;
        Contact contact = contactList.get(number);
        contact.displayInfo();

        handleRecord(contact, number);
    }

    private void handleRecord(Contact contact, int number) {
        do {
            String recordAction = displayRecordMenu();
            if (recordAction.equalsIgnoreCase("menu")) break;
            if (recordAction.equalsIgnoreCase("edit")) {
                contact = ContactEditService.edit(contact, scanner);
                contactList.set(number, contact);
            } else if (recordAction.equalsIgnoreCase("delete")) {
                contactList.remove(contact);
                System.out.println("The record removed!");
            }
        } while(true);
    }

    public void searchEntries() {
        System.out.println("Enter search query: ");
        String query = scanner.nextLine();

        List<Contact> searchedList = ContactSearchEntryService.search(query, contactList);

        if (searchedList.size() == 0) {
            System.out.println("No results found.");
        } else {
            System.out.printf("Found %s results:", searchedList.size());
            ContactListService.list(searchedList);
            String action = displaySearchMenu();
            if (action.equalsIgnoreCase("back")) return;
            if (action.equalsIgnoreCase("again")) { searchEntries(); return; }

            int number = Integer.parseInt(action) - 1;
            Contact contact = contactList.get(number);
            contact.displayInfo();
            handleRecord(contact, number);
        }
    }




    public void countEntries() {
        System.out.printf("The Phone Book has %s records", contactList.size());
    }
}
