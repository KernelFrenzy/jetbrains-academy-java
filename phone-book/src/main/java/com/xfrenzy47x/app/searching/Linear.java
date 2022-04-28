package com.xfrenzy47x.app.searching;

import com.xfrenzy47x.app.model.PhoneBookEntry;
import com.xfrenzy47x.app.util.TimeCalc;

import java.util.ArrayList;
import java.util.List;

public class Linear {
    private Linear() {}
    public static void search(List<PhoneBookEntry> phoneBookDir, List<PhoneBookEntry> findInDir) {
        int found = 0;
        System.out.println("Start searching (linear search)...");

        long start = System.currentTimeMillis();
        for (PhoneBookEntry entry : findInDir) {
            int result = search(phoneBookDir, entry);
            if (result >= 0) {
                found++;
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("Found " + found + "/" + findInDir.size() + " entries. Time taken: " + TimeCalc.getTimeTaken(start, end));
        System.out.println();
    }

    private static Integer search(List<PhoneBookEntry> phoneBook, PhoneBookEntry toFind) {
        for (int i = 0; i < phoneBook.size(); i++) {
            if (phoneBook.get(i).getName().equals(toFind.getName())) {
                return i;
            }
        }
        return -1;
    }

    public static String[] objectSearch(Object[] phoneBookDir, List<PhoneBookEntry> findInDir) {
        int found = 0;
        List<PhoneBookEntry> phoneBookEntryList = new ArrayList<>();
        for (Object entry : phoneBookDir) {
            phoneBookEntryList.add((PhoneBookEntry) entry);
        }
        long start = System.currentTimeMillis();

        for (PhoneBookEntry entry : findInDir) {
            int result = search(phoneBookEntryList, entry);
            if (result >= 0) {
                found++;
            }
        }
        long end = System.currentTimeMillis();
        String searchTimeTaken =TimeCalc.getTimeTaken(start, end);
        return new String[]{found + "", searchTimeTaken};
    }
}
