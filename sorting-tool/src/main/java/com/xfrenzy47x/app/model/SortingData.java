package com.xfrenzy47x.app.model;

import com.xfrenzy47x.app.enums.DataType;
import com.xfrenzy47x.app.enums.SortingType;

public class SortingData {
    private SortingType sortingType;
    private DataType dataType;

    private String inputFile;
    private String outputFile;

    private String error;

    public SortingData() {
        this.sortingType = SortingType.NATURAL;
        this.dataType = DataType.WORD;
        this.error = "";
        this.inputFile = "";
        this.outputFile = "";
    }

    public SortingType getSortingType() {
        return sortingType;
    }

    public void setSortingType(String sortingType) {
        this.sortingType = SortingType.valueOf(sortingType);
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = DataType.valueOf(dataType);
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getInputFile() {
        return inputFile;
    }

    public void setInputFile(String inputFile) {
        this.inputFile = inputFile;
    }

    public String getOutputFile() {
        return outputFile;
    }

    public void setOutputFile(String outputFile) {
        this.outputFile = outputFile;
    }
}
