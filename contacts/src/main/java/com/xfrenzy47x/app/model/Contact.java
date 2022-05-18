package com.xfrenzy47x.app.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public abstract class Contact implements Serializable {
    private static final long serialVersionUID = 1L;

    private String number;
    private final LocalDateTime createdDate;
    private LocalDateTime editedDate;

    protected Contact(String number) {
        this.number = number;
        this.createdDate = LocalDateTime.now();
        this.editedDate = null;
    }

    @Override
    public String toString() {
        return String.format("%s", getNumber());
    }

    private boolean hasNumber() {
        return !number.trim().isEmpty();
    }

    public void setNumber(String number) {
        this.number = number;
        updated();
    }

    public void updated() {
        this.editedDate = LocalDateTime.now();
    }

    public String getNumber() {
        return hasNumber() ? number : "[no data]";
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getEditedDate() {
        return editedDate;
    }

    public abstract String getFullName();
    public abstract void displayInfo();

    public abstract void displayFields();
}

