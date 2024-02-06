package com.example.cs2340gr76.ui.exams;

import androidx.annotation.NonNull;

import java.util.Calendar;

public class ExamModel {
    private int id;
    private String name, location, detail, time;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ExamModel() {
        this.id = -1;
        this.name = null;
        this.location = null;
        this.time = null;
        this.detail = null;
    }

    public ExamModel(int id, String name, String location, String time, String detail) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.detail = detail;
        this.time = time;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format(
                name + "/nDate: " + time.toString() + "/nLocation: " + location + "/n" + detail);
    }


}