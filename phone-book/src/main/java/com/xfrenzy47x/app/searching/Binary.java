package com.xfrenzy47x.app.searching;

import com.xfrenzy47x.app.model.PhoneBookEntry;
import com.xfrenzy47x.app.util.TimeCalc;

import java.util.List;

public class Binary {
    private Binary() {

    }
    public static String[] search(Object[] phoneBookDir, List<PhoneBookEntry> findInDir) {
        int found = 0;
        long start = System.currentTimeMillis();
        for (PhoneBookEntry entry : findInDir) {
            int result = search(phoneBookDir, entry);
            if (result >= 0) {
                found++;
            }
        }
        long end = System.currentTimeMillis();
        String searchTimeTaken = TimeCalc.getTimeTaken(start, end);
        return new String[]{found + "", searchTimeTaken};
    }

    private static int search(Object[] phoneBookDir, PhoneBookEntry findInDir) {
        int left = 0;
        int right = phoneBookDir.length-1;

        while (left <= right) {
            int middle = (left + right) / 2;
            PhoneBookEntry temp = (PhoneBookEntry)phoneBookDir[middle];
            if (temp.getName().equals(findInDir.getName())) {
                return middle;
            } else if (temp.getName().compareTo(findInDir.getName()) > 0) {
                right = middle - 1;
            } else {
                left = middle + 1;
            }
        }
        return -1;
    }
}
