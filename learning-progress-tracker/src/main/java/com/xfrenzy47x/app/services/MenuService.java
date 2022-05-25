package com.xfrenzy47x.app.services;

import java.util.Scanner;

public class MenuService {
    Scanner scanner;
    StudentService studentService;
    StatService statService;
    NotificationService notificationService;
    public MenuService(Scanner scanner) {
        this.scanner = scanner;
        this.studentService = new StudentService(scanner);
        this.statService = new StatService(scanner);
        this.notificationService = new NotificationService();
    }

    public void run() {
        System.out.println("Learning Progress Tracker");
        String input = "";

        while (!input.equalsIgnoreCase("exit")) {
            input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("No input");
            } else if (input.equalsIgnoreCase("exit")) {
                System.out.println("Bye!");
            } else if (input.equalsIgnoreCase("add students")){
                studentService.addStudents();
            } else if (input.equalsIgnoreCase("list")) {
                studentService.listStudents();
            } else if (input.equalsIgnoreCase("add points")) {
                studentService.addPoints();
            } else if (input.equalsIgnoreCase("back")) {
                System.out.println("Enter 'exit' to exit the program");
            } else if (input.equalsIgnoreCase("find")) {
                studentService.findStudents();
            } else if (input.equalsIgnoreCase("statistics")) {
                statService.stats(studentService.getStudents());
            } else if (input.equalsIgnoreCase("notify")) {
                notificationService.notifyStudents(studentService.getStudents());
            } else {
                System.out.println("Unknown command!");
            }
        }
    }
}
