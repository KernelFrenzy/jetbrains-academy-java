package com.xfrenzy47x.app.model;

import com.xfrenzy47x.app.util.Constant;

import java.time.LocalDate;

public class Person extends Contact {
    private String name;
    private String surname;

    private LocalDate birthDate;

    private Character gender;


    public Person(String name, String surname, LocalDate birthDate, Character gender, String number) {
        super(number);
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.birthDate = birthDate;
    }

    public void setName(String name) {
        this.name = name;
        updated();
    }

    public void setSurname(String surname) {
        this.surname = surname;
        updated();
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        updated();
    }

    public void setGender(Character gender) {
        this.gender = gender;
        updated();
    }



    @Override
    public String toString() {
        return String.format("%s %s, %s", this.name, this.surname, getNumber());
    }

    @Override
    public String getFullName() {
        return this.name + " " + this.surname;
    }

    @Override
    public void displayInfo() {
        StringBuilder builder = new StringBuilder();
        builder.append("Name: ").append(!this.name.isEmpty() ? this.name : Constant.NO_DATA).append("\n");
        builder.append("Surname: ").append(!this.surname.isEmpty() ? this.surname : Constant.NO_DATA).append("\n");
        builder.append("Birth date: ").append(this.birthDate != null ? this.birthDate : Constant.NO_DATA).append("\n");
        builder.append("Gender: ").append(this.gender != null ? this.gender : Constant.NO_DATA).append("\n");
        builder.append("Number: ").append(!this.getNumber().isEmpty() ? this.getNumber() : Constant.NO_DATA).append("\n");
        builder.append("Time created: ").append(this.getCreatedDate() != null ? this.getCreatedDate() : Constant.NO_DATA).append("\n");
        builder.append("Time last edit: ").append(this.getEditedDate() != null ? this.getEditedDate() : Constant.NO_DATA).append("\n");
        System.out.println(builder);
    }

    @Override
    public void displayFields() {
        System.out.println("Select a field (name, surname, birth, gender, number): ");
    }
}

