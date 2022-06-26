package com.xfrenzy47x.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity()
@Table(name = "CODE_PLATFORM_PAGE")
public class CodePlatformPage {

    @JsonIgnore
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "CODE")
    private String code;

    @Column(name = "DATE")
    private String date;

    @Column(name = "TIME")
    private Integer time;

    @Column(name = "VIEWS")
    private Integer views;

    @JsonIgnore
    @Column(name = "ORIGINAL_TIME")
    private Integer originalTime;

    @JsonIgnore
    @Column(name = "TIME_RESTRICTED")
    private Boolean timeRestricted;

    @JsonIgnore
    @Column(name = "VIEWS_RESTRICTED")
    private Boolean viewsRestricted;

    @JsonIgnore
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

    public CodePlatformPage() {}

    public CodePlatformPage(String code, Integer time, Integer views) {
        this.id = UUID.randomUUID().toString();
        this.code = code;
        this.createdDate = LocalDateTime.now();
        this.date = this.createdDate + "";
        this.time = time;
        this.originalTime = time;
        this.views = views;
        this.timeRestricted = time > 0;
        this.viewsRestricted = views > 0;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getOriginalTime() {
        return originalTime;
    }

    public void setOriginalTime(Integer originalTime) {
        this.originalTime = originalTime;
    }

    public Boolean getTimeRestricted() {
        return timeRestricted;
    }

    public void setTimeRestricted(Boolean timeRestricted) {
        this.timeRestricted = timeRestricted;
    }

    public Boolean getViewsRestricted() {
        return viewsRestricted;
    }

    public void setViewsRestricted(Boolean viewsRestricted) {
        this.viewsRestricted = viewsRestricted;
    }

    @JsonIgnore
    @Override
    public String toString() {
        return "CodePlatformPage{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", date='" + date + '\'' +
                ", time=" + time +
                ", views=" + views +
                ", originalTime=" + originalTime +
                ", timeRestricted=" + timeRestricted +
                ", viewsRestricted=" + viewsRestricted +
                ", createdDate=" + createdDate +
                '}';
    }
}
