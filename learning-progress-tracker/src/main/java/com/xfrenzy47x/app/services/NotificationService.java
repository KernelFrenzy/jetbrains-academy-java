package com.xfrenzy47x.app.services;

import com.xfrenzy47x.app.models.Student;

import java.util.List;

public class NotificationService {

    String notification;
    boolean notificationAddedForStudent;

    public void notifyStudents(List<Student> students) {
        notification = "";
        int number = 0;
        for (Student student : students) {
            notificationAddedForStudent = false;

            handleJavaNotification(student);
            handleDbNotification(student);
            handleDSANotification(student);
            handleSpringNotification(student);

            number += notificationAddedForStudent ? 1 : 0;
        }

        if (!notification.isEmpty()) {
            System.out.print(notification);
        }

        System.out.println("Total " + number + " students have been notified.");

    }

    private void handleDSANotification(Student student) {
        if (student.getDsa().isComplete() && !student.getDsa().isNotified()) {
            notification += fillTemplate(student.getEmail(), student.getFullName(), "DSA");
            student.getDsa().notificationSent();
            notificationAddedForStudent = true;
        }
    }

    private void handleSpringNotification(Student student) {
        if (student.getSpring().isComplete() && !student.getSpring().isNotified()) {
            notification += fillTemplate(student.getEmail(), student.getFullName(), "Spring");
            student.getSpring().notificationSent();
            notificationAddedForStudent = true;
        }

    }

    private void handleJavaNotification(Student student) {
        if (student.getJava().isComplete() && !student.getJava().isNotified()) {
            notification += fillTemplate(student.getEmail(), student.getFullName(), "Java");
            student.getJava().notificationSent();
            notificationAddedForStudent = true;
        }
    }

    private void handleDbNotification(Student student) {
        if (student.getDb().isComplete() && !student.getDb().isNotified()) {
            notification += fillTemplate(student.getEmail(), student.getFullName(), "Databases");
            student.getDb().notificationSent();
            notificationAddedForStudent = true;
        }
    }

    private String getTemplate() {
        return """
                To: %EMAIL_ADDRESS%
                Re: Your Learning Progress
                Hello, %FULL_USER_NAME%! You have accomplished our %COURSE_NAME% course!
                """;
    }

    private String fillTemplate(String email, String fullName, String course) {
        String template = getTemplate();

        template = template.replace("%EMAIL_ADDRESS%", email);
        template = template.replace("%FULL_USER_NAME%", fullName);
        template = template.replace("%COURSE_NAME%", course);

        return template;
    }

}
