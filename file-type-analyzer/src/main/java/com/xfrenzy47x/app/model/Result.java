package com.xfrenzy47x.app.model;

import java.io.File;

public class Result {
    private File file;
    private Pattern pattern;

    public Result(File file, Pattern pattern) {
        this.file = file;
        this.pattern = pattern;
    }

    public File getFile() {return this.file;}

    public Pattern getPattern() { return this.pattern; }
}
