package com.xfrenzy47x.app.service;

import com.xfrenzy47x.app.model.Contact;

import java.util.List;
import java.util.stream.Collectors;

public class ContactSearchEntryService {
    private ContactSearchEntryService() {
        throw new IllegalStateException("Utility class");
    }
    public static List<Contact> search(String query, List<Contact> contactList) {
        final String queryFinal = query;
        return contactList.stream().filter(x -> x.toString().toLowerCase().contains(queryFinal)).collect(Collectors.toList());
    }
}
