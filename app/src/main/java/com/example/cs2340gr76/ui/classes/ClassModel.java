package com.example.cs2340gr76.ui.classes;

import androidx.annotation.NonNull;

import java.util.List;

public class ClassModel {

    private String className;
    private String meetingTime;
    private List<String> selectedDays;
    private String location;

    // Constructors
    public ClassModel(String className, String meetingTime, String location, List<String> selectedDays) {
        this.className = className;
        this.meetingTime = meetingTime;
        this.selectedDays = selectedDays;
        this.location = location;
    }

    // Getters and setters
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMeetingTime() {
        return meetingTime;
    }

    public void setMeetingTime(String meetingTime) {
        this.meetingTime = meetingTime;
    }

    public List<String> getSelectedDays() {
        return selectedDays;
    }

    public void setSelectedDays(List<String> selectedDays) {
        this.selectedDays = selectedDays;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @NonNull
    @Override
    public String toString() {
        return "Class: " + className + "\n"
                + "Meeting Time: " + meetingTime + "\n"
                + "Selected Days: " + selectedDays + "\n"
                + "Location: " + location;
    }
}
