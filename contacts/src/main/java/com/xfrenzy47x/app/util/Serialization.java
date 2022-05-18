package com.xfrenzy47x.app.util;

import com.xfrenzy47x.app.model.Contact;
import com.xfrenzy47x.app.service.ContactService;

import java.io.*;
import java.util.List;

public class Serialization {
    private Serialization() {
        throw new IllegalStateException("Utility class");
    }
    public static void serialize(Object obj, String fileName) {
        try (FileOutputStream fos = new FileOutputStream(fileName);
             BufferedOutputStream bos = new BufferedOutputStream(fos);
             ObjectOutputStream oos = new ObjectOutputStream(bos);) {
            oos.writeObject(obj);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static Object deserialize(String fileName) {
        try (FileInputStream fis = new FileInputStream(fileName);
             BufferedInputStream bis = new BufferedInputStream(fis);
             ObjectInputStream ois = new ObjectInputStream(bis);) {
            return ois.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static ContactService initializeContactService() {
        ContactService contactService = new ContactService();
        Object obj = deserialize(contactService.getPhoneBookPath());
        if (obj != null) {
            List<Contact> contactList = (List<Contact>) obj;
            contactService.loadContacts(contactList);
        }
        return contactService;
    }

    public static void createFile(File file) {
        try {
            file.createNewFile();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
