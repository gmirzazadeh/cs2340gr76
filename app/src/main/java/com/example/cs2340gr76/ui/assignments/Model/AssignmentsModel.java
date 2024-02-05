package com.example.cs2340gr76.ui.assignments.Model;

public class AssignmentsModel {
    public static final String TABLE_NAME = "assignments";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_CLASSNAME = "className";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_TIMESTAMP = "time";

    private int id;
    private String title;
    private String date;
    private String time;
    private String className;

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_TITLE + " TEXT,"
                    + COLUMN_CLASSNAME + " TEXT,"
                    + COLUMN_DATE + " TEXT,"
                    + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    public AssignmentsModel(String title, String date, String time, String className, String description) {
        this.title = title;
        this.date = date;
        this.time = time;
        this.className = className;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getClassName() {
        return className.toUpperCase();
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
