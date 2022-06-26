package com.xfrenzy47x.dto;

public class NewCodeRequestDto {
    private String code;
    private Integer time;
    private Integer views;

    public NewCodeRequestDto() {

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    @Override
    public String toString() {
        return "NewCodeRequestDto{" +
                "code='" + code + '\'' +
                ", time=" + time +
                ", views=" + views +
                '}';
    }
}

