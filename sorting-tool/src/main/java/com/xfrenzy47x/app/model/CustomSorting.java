package com.xfrenzy47x.app.model;

import com.xfrenzy47x.app.util.Helper;

public class CustomSorting {
    String message;
    String stringValue;
    Long longValue;
    Long appearances;

    public CustomSorting(Long appearances, Long longValue, String stringValue, int size) {
        this.message = String.format(stringValue.isEmpty() ? longValue + "" : stringValue + ": " + appearances + " time(s), " + Helper.getPercentage(appearances, size) + "%");
        this.appearances = appearances;
        this.longValue = longValue;
        this.stringValue = stringValue;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public Long getLongValue() {
        return longValue;
    }

    public void setLongValue(Long longValue) {
        this.longValue = longValue;
    }

    public Long getAppearances() {
        return appearances;
    }

    public void setAppearances(Long appearances) {
        this.appearances = appearances;
    }
}
