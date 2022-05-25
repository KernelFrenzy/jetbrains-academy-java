package com.xfrenzy47x.app.models;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Course {
    private String name;
    private int score;
    private int submissions;

    private final int scoreRequired;

    private boolean notified;

    public Course(String name, int scoreRequired) {
        this.name = name;
        this.score = 0;
        this.submissions = 0;
        this.scoreRequired = scoreRequired;
        this.notified = false;
    }

    public void addScore(int score) {
        if (score > 0) {
            this.score += score;
            this.submissions++;
        }
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public String percentageComplete() {
        BigDecimal completed = BigDecimal.valueOf((double) this.score / this.scoreRequired * 100);
        completed = completed.setScale(1, RoundingMode.HALF_UP);
        return completed + "%";
    }

    public boolean isComplete() {
        return ((double) this.score / this.scoreRequired) >= 1;
    }

    public int getSubmissions() {
        return submissions;
    }

    public void notificationSent() {
        this.notified = true;
    }

    public boolean isNotified() {
        return this.notified;
    }
}
