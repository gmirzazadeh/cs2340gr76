package com.example.cs2340gr76.ui.exams;

import androidx.annotation.NonNull;

public class ExamsModel {
    public static final String TABLE_NAME = "exams";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_EXAMNAME = "name";
    public static final String COLUMN_LOCATION = "location";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_TIME = "time";

    private int id;
    private String examName, location, date, time;

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_EXAMNAME + " TEXT,"
                    + COLUMN_LOCATION + " TEXT,"
                    + COLUMN_DATE + " TEXT,"
                    + COLUMN_TIME + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    public String getName() {
        return examName;
    }

    public void setName(String examName) {
        this.examName = examName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public ExamsModel(String examName, String location, String date, String time) {
        this.examName = examName;
        this.location = location;
        this.date = date;
        this.time = time;
    }

//    @NonNull
//    @Override
//    public String toString() {
//        return String.format(
//                name + "/nDate: " + time.toString() + "/nLocation: " + location + "/n" + detail);
//    }
//

}