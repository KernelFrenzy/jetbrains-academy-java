package com.xfrenzy47x.app.services;

import com.xfrenzy47x.app.comparator.StatComparator;
import com.xfrenzy47x.app.models.Stat;
import com.xfrenzy47x.app.models.Student;
import com.xfrenzy47x.app.utilities.StringHelper;

import java.util.*;
import java.util.stream.Collectors;

public class StudentService {
    private List<Student> students;
    private Scanner scanner;
    public StudentService(Scanner scanner) {
        this.students = new ArrayList<>();
        this.scanner = scanner;
    }

    public void addStudents() {
        System.out.println("Enter student credentials or 'back' to return");
        String credsOrBack = "";
        int studentsAdded = 0;
        while (!credsOrBack.trim().equalsIgnoreCase("back")) {
            credsOrBack = scanner.nextLine();
            if (!credsOrBack.trim().equalsIgnoreCase("back") && handleNewStudent(credsOrBack)) {
                studentsAdded++;
            }
        }
        System.out.println(String.format("Total %s students have been added.", studentsAdded));
    }

    private boolean handleNewStudent(String creds) {
        boolean add = false;
        String[] data = creds.trim().split(" ");
        String firstName;
        String lastName = "";
        String email;

        if (data.length < 3) {
            System.out.println("Incorrect credentials.");
        } else {
            firstName = data[0];
            email = data[data.length-1];
            StringBuilder lastNameSB = new StringBuilder();
            for (int i = 1; i < data.length-1; i++) {
                lastNameSB.append(data[i]).append(" ");
            }
            lastName = lastNameSB.toString().trim();

            add = true;
            if (StringHelper.notValidName(firstName)) {
                System.out.println("Incorrect first name.");
                add = false;
            } else if (StringHelper.notValidName(lastName)) {
                System.out.println("Incorrect last name.");
                add = false;
            } else if (!StringHelper.isValidEmail(email)) {
                System.out.println("Incorrect email.");
                add = false;
            } else if (students.stream().filter(x -> x.getEmail().equalsIgnoreCase(email)).findAny().orElse(null) != null) {
                System.out.println("This email is already taken.");
                add = false;
            }

            if (firstName.equalsIgnoreCase(lastName) && lastName.equalsIgnoreCase(email)) {
                System.out.println("Incorrect credentials.");
                add = false;
            }

            if (add) {
                students.add(new Student(generateId(), firstName, lastName, email));
                System.out.println("The student has been added.");
            }
        }

        return add;
    }

    public void listStudents() {
        if (students.size() == 0) {
            System.out.println("No students found");
            return;
        }
        System.out.println("Students: ");
        students.forEach(x -> System.out.println(x.getId()));
    }

    public void addPoints() {
        System.out.println("Enter an id and points or 'back' to return");
        String pointsOrBack = "";
        while (!pointsOrBack.equalsIgnoreCase("back")) {
            pointsOrBack = scanner.nextLine();
            if (!pointsOrBack.trim().equalsIgnoreCase("back")) {
                String[] parts = pointsOrBack.split(" ");
                handleAddingPoints(parts);
            }
        }
    }

    private void handleAddingPoints(String[] parts) {
        Student student = students.stream().filter(x -> x.getId().equals(parts[0])).findFirst().orElse(null);
        boolean added = false;
        if (student != null) {
            List<Integer> scores = new ArrayList<>();
            for (int i = 1; i < parts.length; i++) {
                if (StringHelper.isValidNumber(parts[i])) {
                    scores.add(Integer.parseInt(parts[i]));
                }
            }

            if (scores.size() == 4) {
                students.remove(student);
                student.addScores(scores.get(0), scores.get(1), scores.get(2), scores.get(3));
                students.add(student);
                added = true;
                System.out.println("Points updated");
            }
        } else {
            System.out.println("No student is found for id=" + parts[0]);
        }

        if (!added) {
            System.out.println("Incorrect points format");
        }
    }

    public void findStudents() {
        System.out.println("Enter an id or 'back' to return");
        String idOrBack = "";
        while (!idOrBack.equalsIgnoreCase("back")) {
            idOrBack = scanner.nextLine();
            if (!idOrBack.equalsIgnoreCase("back")) {
                String finalIdOrBack = idOrBack;
                Student student = students.stream().filter(x -> x.getId().equals(finalIdOrBack)).findFirst().orElse(null);
                if (student != null) {
                    System.out.println(student);
                } else {
                    System.out.println("No student is found for id=" + finalIdOrBack);
                }
            }
        }
    }



    private String generateId() {
        return students.size() + 1 + "";
    }

    public List<Student> getStudents() {
        return students;
    }
}
