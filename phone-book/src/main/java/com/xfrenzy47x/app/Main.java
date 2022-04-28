package com.xfrenzy47x.app;

import com.xfrenzy47x.app.model.PhoneBookEntry;
import com.xfrenzy47x.app.searching.Linear;
import com.xfrenzy47x.app.sorting.Bubble;
import com.xfrenzy47x.app.sorting.Quick;
import com.xfrenzy47x.app.util.Helper;
import com.xfrenzy47x.app.util.TimeCalc;

import java.io.File;
import java.net.URISyntaxException;
import java.util.*;


public class Main {
    public static void main(String[] args) throws URISyntaxException {

        File directory = Helper.getFile("small_directory.txt");
        File find = Helper.getFile("small_find.txt");

        List<PhoneBookEntry> phoneBookDir = Helper.populatePhoneBookEntry(directory, true);
        List<PhoneBookEntry> findInDir = Helper.populatePhoneBookEntry(find, false);

        Linear.search(phoneBookDir, findInDir);
        Bubble.bubbleSortAndSearch(phoneBookDir, findInDir);
        Quick.sortAndBinarySearch(phoneBookDir, findInDir);
        hashTableCreationAndSearch(phoneBookDir, findInDir);
    }

    //region Hash table

    public static void hashTableCreationAndSearch(List<PhoneBookEntry> phoneBookDir, List<PhoneBookEntry> findInDir) {
        System.out.println("Start searching (hash table)...");
        long start = System.currentTimeMillis();
        Map<String, PhoneBookEntry> hashtable = createHashTable(phoneBookDir);
        long sortingEnded = System.currentTimeMillis();
        String[] result = hashSearch(hashtable, findInDir);
        long end = System.currentTimeMillis();
        String totalTimeTaken = TimeCalc.getTimeTaken(start, end);

        System.out.println("Found " + result[0] + "/" + findInDir.size() + " entries. Time taken: " + totalTimeTaken);
        System.out.println("Creating time: " +TimeCalc.getTimeTaken(start, sortingEnded));
        System.out.println("Searching time: " + result[1]);
        System.out.println();
    }

    public static String[] hashSearch(Map<String, PhoneBookEntry> hashtable, List<PhoneBookEntry> findInDir) {
        int found = 0;
        long start = System.currentTimeMillis();
        for (PhoneBookEntry entry : findInDir) {
            PhoneBookEntry foundEntry = hashtable.get(entry.getName());
            if (foundEntry != null) {
                found++;
            }
        }
        long end = System.currentTimeMillis();
        String searchTimeTaken =TimeCalc.getTimeTaken(start, end);
        return new String[]{found + "", searchTimeTaken};
    }

    public static Map<String, PhoneBookEntry> createHashTable(List<PhoneBookEntry> phoneBookDir) {
        Map<String, PhoneBookEntry> hashtable = new HashMap<>();
        for (PhoneBookEntry entry : phoneBookDir) {
            hashtable.put(entry.getName(), entry);
        }
        return hashtable;
    }

    //endregion




}
