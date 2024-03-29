package com.example.cs2340gr76.ui.exams;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



import java.util.ArrayList;
import java.util.List;

public class ExamsDataHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "exams_db";
    private static final String EXAMS_TABLE = "exams";
    private static final String ID = "id";
    private static final String EXAMNAME = "name";
    private static final String LOCATION = "location";
    private static final String DATE = "date";
    private static final String TIME = "time";
    private static final String CREATE_EXAMS_TABLE = "CREATE TABLE " + EXAMS_TABLE + "(" +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            EXAMNAME + " TEXT, " +
            LOCATION + " TEXT, " +
            DATE + " TEXT, " +
            TIME + " TEXT)";

    private SQLiteDatabase db;

    public ExamsDataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_EXAMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + EXAMS_TABLE);
        onCreate(db);
    }

    public void openDatabase() {
        db = this.getWritableDatabase();
    }



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

    public List<ExamsModel> getAllExams() {
        List<ExamsModel> examsList = new ArrayList<>();
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
                        cursor.getString(cursor.getColumnIndex(ExamsModel.COLUMN_TIME))
                );
                exam.setId(cursor.getInt(cursor.getColumnIndex(ExamsModel.COLUMN_ID)));
                examsList.add(exam);
            } while (cursor.moveToNext());

            cursor.close();
        }

        return examsList;
    }

    public int updateExam(ExamsModel exam) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(ExamsModel.COLUMN_EXAMNAME, exam.getName());
        cv.put(ExamsModel.COLUMN_LOCATION, exam.getLocation());
        cv.put(ExamsModel.COLUMN_DATE, exam.getDate());
        cv.put(ExamsModel.COLUMN_LOCATION, exam.getTime());

        return db.update(ExamsModel.TABLE_NAME, cv, ExamsModel.COLUMN_ID + " = ?",
                new String[] {String.valueOf(exam.getId())});


    }

    public void closeDatabase() {
        if (db != null && db.isOpen()) {
            db.close();
        }
    }

    public void deleteExam(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ExamsModel.TABLE_NAME, ExamsModel.COLUMN_ID + "= ?",
                new String[] {String.valueOf(id)});
        db.close();
    }
}