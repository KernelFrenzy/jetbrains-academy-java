package com.xfrenzy47x.app.comparator;

import com.xfrenzy47x.app.model.CustomSorting;

import java.util.Comparator;
import java.util.Objects;

public class CustomSortingStringComparator implements Comparator<CustomSorting> {

    @Override
    public int compare(CustomSorting x, CustomSorting y) {
        if (Objects.equals(x.getAppearances(), y.getAppearances())) {
            return x.getStringValue().compareTo(y.getStringValue());
        } else {
            return Long.compare(x.getAppearances(), y.getAppearances());
        }
    }
}
