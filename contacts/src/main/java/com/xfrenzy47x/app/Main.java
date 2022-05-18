package com.xfrenzy47x.app;


import com.xfrenzy47x.app.service.ContactService;
import com.xfrenzy47x.app.util.Serialization;

public class Main {
    public static void main(String[] args) {
        ContactService contactService = Serialization.initializeContactService();

        while (true) {
            String action = contactService.displayMenu();

            if (action.equalsIgnoreCase("exit")) {
                break;
            }

            switch (action) {
                case "add":
                    contactService.addEntry();
                    break;
                case "list":
                    contactService.listEntries();
                    break;
                case "search":
                    contactService.searchEntries();
                    break;
                case "count":
                    contactService.countEntries();
                    break;
            }
            System.out.println();
        }

        contactService.saveContacts();
    }
}
