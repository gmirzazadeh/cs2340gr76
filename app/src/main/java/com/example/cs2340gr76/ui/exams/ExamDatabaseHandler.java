package com.example.cs2340gr76.ui.exams;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExamDatabaseHandler extends SQLiteOpenHelper {

    private static final int VERSION = 5;
    private static final String BASENAME = "examListDatabase";
    private static final String EXAM_TABLE = "exam";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String LOCATION = "location";
    private static final String DATE = "date";

    private static final String TIME = "time";
    private static final String CREATE_EXAM_TABLE = "CREATE TABLE " + EXAM_TABLE + "(" +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            NAME + " TEXT, " +
            LOCATION + " TEXT, " +
            DATE + " TEXT, " +
            TIME + " TEXT)";

    private SQLiteDatabase db;

    public ExamDatabaseHandler(Context context) {
        super(context, BASENAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the exams table
        db.execSQL(ExamsModel.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if it exists and recreate the table
        db.execSQL("DROP TABLE IF EXISTS " + ExamsModel.TABLE_NAME);
        onCreate(db);
    }

    public void openDatabase() {
        db = this.getWritableDatabase();
    }

    // Close the database
    public void closeDatabase() {
        if (db != null && db.isOpen()) {
            db.close();
        }
    }

    // Insert a new exam
    public long insertExam(ExamsModel exam) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ExamsModel.COLUMN_EXAMNAME, exam.getName());
        values.put(ExamsModel.COLUMN_LOCATION, exam.getLocation());
        values.put(ExamsModel.COLUMN_DATE, exam.getDate());
        values.put(ExamsModel.COLUMN_TIME, exam.getTime());

        long id = db.insert(ExamsModel.TABLE_NAME, null, values);
        db.close();
        return id;
    }

    public ExamsModel getExam(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                ExamsModel.TABLE_NAME,
                new String[]{ExamsModel.COLUMN_ID, ExamsModel.COLUMN_EXAMNAME,
                        ExamsModel.COLUMN_LOCATION, ExamsModel.COLUMN_DATE,
                        ExamsModel.COLUMN_TIME},
                ExamsModel.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)},
                null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();

            ExamsModel exam = new ExamsModel(
                    cursor.getString(cursor.getColumnIndex(ExamsModel.COLUMN_EXAMNAME)),
                    cursor.getString(cursor.getColumnIndex(ExamsModel.COLUMN_LOCATION)),
                    cursor.getString(cursor.getColumnIndex(ExamsModel.COLUMN_DATE)),
                    cursor.getString(cursor.getColumnIndex(ExamsModel.COLUMN_TIME)));

            cursor.close();
            return exam;
        }

        return null;
    }

    // Get all exam
    public List<ExamsModel> getAllExams() {
        List<ExamsModel> exams = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                ExamsModel.TABLE_NAME,
                new String[]{ExamsModel.COLUMN_ID, ExamsModel.COLUMN_EXAMNAME,
                        ExamsModel.COLUMN_LOCATION, ExamsModel.COLUMN_DATE,
                        ExamsModel.COLUMN_TIME},
                null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                ExamsModel exam = new ExamsModel(
                        cursor.getString(cursor.getColumnIndex(ExamsModel.COLUMN_EXAMNAME)),
                        cursor.getString(cursor.getColumnIndex(ExamsModel.COLUMN_LOCATION)),
                        cursor.getString(cursor.getColumnIndex(ExamsModel.COLUMN_DATE)),
                        cursor.getString(cursor.getColumnIndex(ExamsModel.COLUMN_TIME)));

                exam.setId(cursor.getInt(cursor.getColumnIndex(ExamsModel.COLUMN_ID)));
                exams.add(exam);
            } while (cursor.moveToNext());

            cursor.close();
        }

        return exams;
    }

    // Update an exam
    public int updateExam(ExamsModel exam) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ExamsModel.COLUMN_EXAMNAME, exam.getName());
        values.put(ExamsModel.COLUMN_LOCATION, exam.getLocation());
        values.put(ExamsModel.COLUMN_DATE, exam.getDate());
        values.put(ExamsModel.COLUMN_TIME, exam.getTime());

        return db.update(ExamsModel.TABLE_NAME, values, ExamsModel.COLUMN_ID + " = ?",
                new String[]{String.valueOf(exam.getId())});
    }

    // Delete an exam
    public void deleteExam(ExamsModel exam) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ExamsModel.TABLE_NAME, ExamsModel.COLUMN_ID + " = ?",
                new String[]{String.valueOf(exam.getId())});
        db.close();
    }
}