package com.xfrenzy47x.app.searching;

import com.xfrenzy47x.app.model.PhoneBookEntry;
import com.xfrenzy47x.app.util.TimeCalc;

import java.util.List;

public class Jump {
    private Jump() {}
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

    private static Integer search(Object[] phoneBook, PhoneBookEntry toFind) {
        int step = (int) Math.floor(Math.sqrt(phoneBook.length));
        int n = phoneBook.length;
        int prev = 0;
        boolean stillLooking = true;
        while(stillLooking) {
            PhoneBookEntry check = (PhoneBookEntry) phoneBook[Math.min(step, n)-1];
            stillLooking = (check.getName().compareTo(toFind.getName()) < 0);
            if (stillLooking) {
                prev = step;
                step += (int) Math.floor(Math.sqrt(n));
                if (prev >= n)
                    return -1;
            }
        }


        do {
            PhoneBookEntry check = (PhoneBookEntry) phoneBook[prev];
            stillLooking = (check.getName().compareTo(toFind.getName()) < 0);

            if (stillLooking) {
                prev++;
                if (prev == Math.min(step, n))
                    return -1;
            }
        } while (stillLooking);

        PhoneBookEntry check = (PhoneBookEntry) phoneBook[prev];
        if (check.getName().equals(toFind.getName())) {
            return prev;
        }

        return -1;
    }
}
