package com.xfrenzy47x.app.comparator;

import com.xfrenzy47x.app.models.Stat;

import java.util.Comparator;

public class StatComparator implements Comparator<Stat> {

    @Override
    public int compare(Stat stat1, Stat stat2) {
        return stat1.getValue().compareTo(stat2.getValue());
    }

    @Override
    public Comparator<Stat> reversed() {
        return Comparator.super.reversed();
    }
}

