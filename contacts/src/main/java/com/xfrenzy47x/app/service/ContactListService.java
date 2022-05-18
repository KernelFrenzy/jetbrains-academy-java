package com.xfrenzy47x.app.service;

import com.xfrenzy47x.app.model.Contact;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ContactListService {
    private ContactListService() {
        throw new IllegalStateException("Utility class");
    }
    public static void list(List<Contact> contactList) {
        if (contactList.size() > 0) {
            AtomicInteger index = new AtomicInteger(0);
            contactList.forEach((contact -> {
                index.getAndIncrement();
                System.out.printf("%s. %s\n", index.get(), contact.getFullName());
            }));
            System.out.println();
        } else {
            System.out.println("No records to view!");
        }
    }
}
