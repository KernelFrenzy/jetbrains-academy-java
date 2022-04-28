package com.xfrenzy47x.app.sorting;

import com.xfrenzy47x.app.model.PhoneBookEntry;
import com.xfrenzy47x.app.searching.Jump;
import com.xfrenzy47x.app.searching.Linear;
import com.xfrenzy47x.app.util.TimeCalc;

import java.util.List;

public class Bubble {
    private Bubble() {

    }
    public static void bubbleSortAndSearch(List<PhoneBookEntry> phoneBookDir, List<PhoneBookEntry> findInDir) {
        System.out.println("Start searching (bubble sort + jump search)...");
        long start = System.currentTimeMillis();
        Object[] sortedPhoneBookDir = sort(phoneBookDir);
        long sortingEnded = System.currentTimeMillis();
        String[] result;
        if (TimeCalc.tookLong(start, sortingEnded)) {
            result = Linear.objectSearch(sortedPhoneBookDir, findInDir);
        } else {
            result = Jump.search(sortedPhoneBookDir, findInDir);
        }
        long end = System.currentTimeMillis();
        String totalTimeTaken =TimeCalc.getTimeTaken(start, end);

        System.out.println("Found " + result[0] + "/" + findInDir.size() + " entries. Time taken: " + totalTimeTaken);
        System.out.println("Sorting time: " +TimeCalc.getTimeTaken(start, sortingEnded) + (TimeCalc.tookLong(start, sortingEnded) ? " - STOPPED, moved to linear search" : ""));
        System.out.println("Searching time: " + result[1]);
        System.out.println();
    }

    public static Object[] sort(List<PhoneBookEntry> list) {
        Object[] objects = list.toArray();
        long start = System.currentTimeMillis();
        boolean changesMade = true;
        while (changesMade) {
            changesMade = false;
            long end = System.currentTimeMillis();
            if (TimeCalc.tookLong(start, end)) {
                break;
            }
            for (int i = 0; i < objects.length; i++) {
                int nextVal = i + 1;
                if (nextVal < objects.length) {
                    PhoneBookEntry curr = (PhoneBookEntry) objects[i];
                    PhoneBookEntry next = (PhoneBookEntry) objects[nextVal];
                    if (curr.getName().compareTo(next.getName()) > 0) {
                        objects[i] = next;
                        objects[nextVal] = curr;
                        changesMade = true;
                    }
                }
            }
        }
        return objects;
    }
}
