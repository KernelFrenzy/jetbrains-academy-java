package com.xfrenzy47x.app.model;

import com.xfrenzy47x.app.util.Constant;

public class Company extends Contact {
    private String name;
    private String address;

    public Company(String name, String address, String number) {
        super(number);
        this.name = name;
        this.address = address;
    }

    public void setName(String name) {
        this.name = name;
        updated();
    }

    public void setAddress(String address) {
        this.address = address;
        updated();
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %s", this.name, this.address, getNumber());
    }

    @Override
    public String getFullName() {
        return this.name;
    }

    @Override
    public void displayInfo() {
        StringBuilder builder = new StringBuilder();
        builder.append("Organization name: ").append(!this.name.isEmpty() ? this.name : Constant.NO_DATA).append("\n");
        builder.append("Address: ").append(!this.address.isEmpty() ? this.address : Constant.NO_DATA).append("\n");
        builder.append("Number: ").append(!this.getNumber().isEmpty() ? this.getNumber() : Constant.NO_DATA).append("\n");
        builder.append("Time created: ").append(this.getCreatedDate() != null ? this.getCreatedDate() : Constant.NO_DATA).append("\n");
        builder.append("Time last edit: ").append(this.getEditedDate() != null ? this.getEditedDate() : Constant.NO_DATA).append("\n");
        System.out.println(builder);
    }

    @Override
    public void displayFields() {
        System.out.println("Select a field (organization name, address, number): ");
    }
}
