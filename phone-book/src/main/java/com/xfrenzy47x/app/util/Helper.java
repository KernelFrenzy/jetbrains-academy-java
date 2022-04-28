package com.xfrenzy47x.app.util;

import com.xfrenzy47x.app.Main;
import com.xfrenzy47x.app.model.PhoneBookEntry;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Helper {

    private Helper() {}

    public static File getFile(String resourceName) throws IllegalArgumentException, URISyntaxException {
        URL resource = Main.class.getClassLoader().getResource(resourceName);
        if (resource == null) {
            throw new IllegalArgumentException("Resource " + resourceName + " is missing...");
        } else {
            return new File(resource.toURI());
        }
    }

    public static List<PhoneBookEntry> populatePhoneBookEntry(File file, boolean fullEntry) {
        List<PhoneBookEntry> list = new ArrayList<>();

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                list.add(new PhoneBookEntry(line, fullEntry));
            }
        } catch (Exception ex) {
            // Whoops
        }

        return list;
    }
}
