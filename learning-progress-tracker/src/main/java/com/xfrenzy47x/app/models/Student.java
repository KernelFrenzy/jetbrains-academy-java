package com.xfrenzy47x.app.models;

public class Student {

    private String id;
    private String firstName;
    private String lastName;
    private String email;

    private Course java;

    private Course dsa;

    private Course db;

    private Course spring;


    public Student(String id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;

        this.java = new Course("Java", 600);
        this.dsa = new Course("DSA", 400);
        this.db = new Course("Databases", 480);
        this.spring = new Course("Spring", 550);
    }

    public void addScores(int java, int dsa, int db, int spring) {
        this.java.addScore(java);
        this.dsa.addScore(dsa);
        this.db.addScore(db);
        this.spring.addScore(spring);
    }

    public String getId() {
        return this.id;
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    public String getEmail() {
        return this.email;
    }

    @Override
    public String toString() {
        return String.format("%s points: Java=%d; DSA=%d; Databases=%d; Spring=%d", id, getJavaScore(), getDsaScore(), getDbScore(), getSpringScore());
    }

    public int getJavaScore() {
        return java.getScore();
    }

    public int getJavaSubs() {
        return java.getSubmissions();
    }

    public int getDsaScore() {
        return dsa.getScore();
    }

    public int getDsaSubs() {
        return dsa.getSubmissions();
    }

    public int getDbScore() {
        return db.getScore();
    }

    public int getDbSubs() {
        return db.getSubmissions();
    }

    public int getSpringScore() {
        return spring.getScore();
    }

    public int getSpringSubs() {
        return spring.getSubmissions();
    }

    public String getJavaPercentageCompleted() {
        return java.percentageComplete();
    }

    public String getDbPercentageCompleted() {
        return db.percentageComplete();
    }

    public String getDsaPercentageCompleted() {
        return dsa.percentageComplete();
    }

    public String getSpringPercentageCompleted() {
        return spring.percentageComplete();
    }

    public Course getJava() {
        return java;
    }

    public Course getDsa() {
        return dsa;
    }

    public Course getDb() {
        return db;
    }

    public Course getSpring() {
        return spring;
    }
}

