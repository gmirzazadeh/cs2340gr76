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


    @NonNull
    @Override
    public String toString() {
        return String.format(
                name + "/nDate: " + time.toString() + "/nLocation: " + location + "/n" + detail);
    }


}