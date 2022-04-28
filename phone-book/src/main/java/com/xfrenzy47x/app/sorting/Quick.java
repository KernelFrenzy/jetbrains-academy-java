package com.xfrenzy47x.app.sorting;

import com.xfrenzy47x.app.model.PhoneBookEntry;
import com.xfrenzy47x.app.searching.Binary;
import com.xfrenzy47x.app.searching.Linear;
import com.xfrenzy47x.app.util.TimeCalc;

import java.util.List;

public class Quick {
    private Quick() {}
    public static void sortAndBinarySearch(List<PhoneBookEntry> phoneBookDir, List<PhoneBookEntry> findInDir) {
        System.out.println("Start searching (quick sort + binary search)...");
        long start = System.currentTimeMillis();
        Object[] sortedPhoneBookDir = quickSort(phoneBookDir.toArray(), 0, phoneBookDir.size()-1);

        long sortingEnded = System.currentTimeMillis();
        String[] result;
        if (TimeCalc.tookLong(start, sortingEnded)) {
            result = Linear.objectSearch(sortedPhoneBookDir, findInDir);
        } else {
            result = Binary.search(sortedPhoneBookDir, findInDir);
        }

        long end = System.currentTimeMillis();
        String totalTimeTaken =TimeCalc.getTimeTaken(start, end);

        System.out.println("Found " + result[0] + "/" + findInDir.size() + " entries. Time taken: " + totalTimeTaken);
        System.out.println("Sorting time: " +TimeCalc.getTimeTaken(start, sortingEnded) + (TimeCalc.tookLong(start, sortingEnded) ? " - STOPPED, moved to linear search" : ""));
        System.out.println("Searching time: " + result[1]);
        System.out.println();
    }

    private static Object[] quickSort(Object[] phoneBookDir, int begin, int end) {
        if (begin < end) {
            int partitionIndex = partition(phoneBookDir, begin, end);

            quickSort(phoneBookDir, begin, partitionIndex-1);
            quickSort(phoneBookDir, partitionIndex+1, end);
        }

        return phoneBookDir;
    }

    private static int partition(Object[] phoneBookDir, int begin, int end) {
        PhoneBookEntry pivot = (PhoneBookEntry)phoneBookDir[end];
        int i = (begin-1);

        for (int j = begin; j < end; j++) {
            PhoneBookEntry temp = (PhoneBookEntry) phoneBookDir[j];
            if (temp.getName().compareTo(pivot.getName()) < 0) {
                i++;

                PhoneBookEntry swapTemp = (PhoneBookEntry)phoneBookDir[i];
                phoneBookDir[i] = phoneBookDir[j];
                phoneBookDir[j] = swapTemp;
            }
        }

        PhoneBookEntry swapTemp = (PhoneBookEntry)phoneBookDir[i+1];
        phoneBookDir[i+1] = phoneBookDir[end];
        phoneBookDir[end] = swapTemp;

        return i+1;
    }
}
