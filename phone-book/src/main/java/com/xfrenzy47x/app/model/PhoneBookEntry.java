package com.xfrenzy47x.app.model;

public class PhoneBookEntry {
    String number;
    String name;

    public PhoneBookEntry(String line, boolean fullEntry) {
        if (fullEntry) {
            String[] split = line.split(" ", 2);
            number = split[0];
            name = split[1];
        } else {
            name = line;
        }
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }
}
