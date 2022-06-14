package com.xfrenzy47x.app.dto;

public class ApiErrorDto {
    private String error;

    public ApiErrorDto() {}

    public ApiErrorDto(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
