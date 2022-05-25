package com.xfrenzy47x.app.services;

import com.xfrenzy47x.app.comparator.StatComparator;
import com.xfrenzy47x.app.models.Stat;
import com.xfrenzy47x.app.models.Student;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class StatService {
    Scanner scanner;
    List<Student> students;
    public StatService(Scanner scanner) {
        this.scanner = scanner;
    }
    public void stats(List<Student> students) {
        this.students = students;
        System.out.println("Type the name of a course to see details or 'back' to quit");
        popular();
        activity();
        difficulty();

        String courseOrBack = "";

        while (!courseOrBack.equalsIgnoreCase("back")) {
            courseOrBack = scanner.nextLine().trim().toLowerCase();
            if (!courseOrBack.equalsIgnoreCase("back")) {
                switch (courseOrBack) {
                    case "java":
                        printJavaDetails();
                        break;
                    case "dsa":
                        printDsaDetails();
                        break;
                    case "databases":
                        printDbDetails();
                        break;
                    case "spring":
                        printSpringDetails();
                        break;
                    default:
                        System.out.println("Unknown course.");
                }
            }
        }
    }

    private void printDetails(String id, String score, String completed) {
        System.out.println(String.format("%s\t%s\t%s", id, score, completed));
    }

    private void printHeading(String course) {
        System.out.println(course);
        System.out.println("id\tpoints\tcompleted");
    }

    private void printJavaDetails() {
        List<Student> theStudents = students.stream().filter(x -> x.getJavaScore() > 0).sorted(Comparator.comparing(Student::getJavaScore).reversed()).collect(Collectors.toList());
        printHeading("Java");
        theStudents.forEach(student -> printDetails(student.getId(), student.getJavaScore() + "", student.getJavaPercentageCompleted()));
    }

    private void printDsaDetails() {
        List<Student> theStudents = students.stream().filter(x -> x.getDsaScore() > 0).sorted(Comparator.comparing(Student::getDsaScore).reversed()).collect(Collectors.toList());
        printHeading("DSA");
        theStudents.forEach(student -> printDetails(student.getId(), student.getDsaScore() + "", student.getDsaPercentageCompleted()));
    }

    private void printDbDetails() {
        List<Student> theStudents = students.stream().filter(x -> x.getDbScore() > 0).sorted(Comparator.comparing(Student::getDbScore).reversed()).collect(Collectors.toList());
        printHeading("Databases");
        theStudents.forEach(student -> printDetails(student.getId(), student.getDbScore() + "", student.getDbPercentageCompleted()));
    }

    private void printSpringDetails() {
        List<Student> theStudents = students.stream().filter(x -> x.getSpringScore() > 0).sorted(Comparator.comparing(Student::getSpringScore).reversed()).collect(Collectors.toList());
        printHeading("Spring");
        theStudents.forEach(student -> printDetails(student.getId(), student.getSpringScore() + "", student.getSpringPercentageCompleted()));
    }

    private void popular() {
        double java = students.stream().filter(x -> x.getJavaScore() > 0).count();
        double dsa = students.stream().filter(x -> x.getDsaScore() > 0).count();
        double db = students.stream().filter(x -> x.getDbScore() > 0).count();
        double spring = students.stream().filter(x -> x.getSpringScore() > 0).count();

        List<Stat> stats = buildStatList(java, dsa, db, spring);

        stats.sort(new StatComparator().reversed());
        String most = getStringValueFromStringDouble(stats, "");
        stats.sort(new StatComparator());
        String least = getStringValueFromStringDouble(stats, most);

        System.out.println("Most popular: " + most);
        System.out.println("Least popular: " + least);
    }

    private void activity() {
        double java = students.stream().filter(x -> x.getJavaSubs() > 0).count();
        double dsa = students.stream().filter(x -> x.getDsaSubs() > 0).count();
        double db = students.stream().filter(x -> x.getDbSubs() > 0).count();
        double spring = students.stream().filter(x -> x.getSpringSubs() > 0).count();

        List<Stat> stats = buildStatList(java, dsa, db, spring);

        stats.sort(new StatComparator().reversed());
        String most = getStringValueFromStringDouble(stats, "");
        stats.sort(new StatComparator());
        String least = getStringValueFromStringDouble(stats, most);


        System.out.println("Highest activity: " + most);
        System.out.println("Lowest activity: " + least);
    }

    private void difficulty() {
        double java = (students.stream().filter(x -> x.getJavaScore() > 0).mapToDouble(Student::getJavaScore).sum() / students.stream().filter(x -> x.getJavaScore() > 0).count() * 100);
        double dsa = (students.stream().filter(x -> x.getDsaScore() > 0).mapToDouble(Student::getDsaScore).sum() / students.stream().filter(x -> x.getDsaScore() > 0).count() * 100);
        double db = (students.stream().filter(x -> x.getDbScore() > 0).mapToDouble(Student::getDbScore).sum() / students.stream().filter(x -> x.getDbScore() > 0).count() * 100);
        double spring = (students.stream().filter(x -> x.getSpringScore() > 0).mapToDouble(Student::getSpringScore).sum() / students.stream().filter(x -> x.getSpringScore() > 0).count() * 100);

        List<Stat> stats = buildStatList(java, dsa, db, spring);

        stats.sort(new StatComparator().reversed());
        String most = getStringValueFromStringDouble(stats, "");
        stats.sort(new StatComparator());
        String least = getStringValueFromStringDouble(stats, most);

        System.out.println("Easiest course: " + most);
        System.out.println("Hardest course: " + least);
    }

    private String getStringValueFromStringDouble(List<Stat> stats, String maxString) {
        String result = "n/a";
        double theValue = 0;
        for (Stat stat : stats) {
            if (!maxString.contains(stat.getName())) {
                if (theValue == 0 && (!Double.isNaN(stat.getValue()) && stat.getValue() != 0)) {
                    theValue = stat.getValue();
                    result = stat.getName();
                } else if (theValue == stat.getValue() && theValue != 0) {
                    result += ", " + stat.getName();
                }
            }
        }

        return result;
    }

    private List<Stat> buildStatList(double java, double dsa, double db, double spring) {
        List<Stat> stats = new ArrayList<>();
        stats.add(new Stat("Java", java));
        stats.add(new Stat("DSA", dsa));
        stats.add(new Stat("Databases", db));
        stats.add(new Stat("Spring", spring));
        return stats;
    }
}
