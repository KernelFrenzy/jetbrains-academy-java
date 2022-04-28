package com.xfrenzy47x.app.comparator;

import com.xfrenzy47x.app.model.PhoneBookEntry;

import java.util.Comparator;

public class PhoneBookComparator implements Comparator<PhoneBookEntry> {
    @Override
    public int compare(PhoneBookEntry phoneBookEntry, PhoneBookEntry t1) {
        return phoneBookEntry.getName().compareTo(t1.getName());
    }
}
