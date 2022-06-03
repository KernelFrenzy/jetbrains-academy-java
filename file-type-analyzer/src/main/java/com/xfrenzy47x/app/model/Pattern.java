package com.xfrenzy47x.app.model;

public class Pattern {
    private Integer priority;
    private String regex;
    private String fileType;

    public Pattern() {
        this.fileType = "Unknown file type";
    }

    public Pattern(String priority, String regex, String fileType) {
        this.priority = Integer.parseInt(priority.replaceAll("\"", ""));
        this.regex = regex.replaceAll("\"", "");
        this.fileType = fileType.replaceAll("\"", "");
    }

    public String getFileType() {
        return this.fileType;
    }

    public Integer getPriority() {
        return this.priority;
    }

    public String getRegex() {
        return this.regex;
    }
}
