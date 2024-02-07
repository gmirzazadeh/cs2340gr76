package com.example.cs2340gr76.ui.exams;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



import java.util.ArrayList;
import java.util.List;

public class ExamDatabaseHandler extends SQLiteOpenHelper {

    private static final int VERSION = 2;
    private static final String BASENAME = "examListDatabase";
    private static final String EXAM_TABLE = "exam";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String LOCATION = "location";
    private static final String DETAILS = "details";

    private static final String TIME = "time";
    private static final String CREATE_EXAM_TABLE = "CREATE TABLE " + EXAM_TABLE + "(" +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            NAME + " TEXT, " +
            LOCATION + " TEXT, " +
            TIME + " TEXT, " +
            DETAILS + " TEXT)";

    private SQLiteDatabase db;

    public ExamDatabaseHandler(Context context) {
        super(context, BASENAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_EXAM_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + EXAM_TABLE);
        // Create tables again
        onCreate(db);
    }

    public void openDatabase() {
        db = this.getWritableDatabase();
    }

    public long insertExam(ExamModel exam){
        ContentValues cv = new ContentValues();
        cv.put(ID, exam.getId());
        cv.put(NAME, exam.getName());
        cv.put(LOCATION, exam.getLocation());
        cv.put(TIME, exam.getTime());
        cv.put(DETAILS, exam.getDetail());
        db.insert(EXAM_TABLE, null, cv);

        long id = db.insert(CREATE_EXAM_TABLE, null, cv);
        db.close();
        return id;
    }

    public List<ExamModel> getAllExams(){
        List<ExamModel> examList = new ArrayList<>();
        Cursor cur = null;
        db.beginTransaction();
        try{
            cur = db.query(EXAM_TABLE, null, null, null, null, null, null, null);
            if(cur != null){
                if(cur.moveToFirst()){
                    do{
                        ExamModel exam = new ExamModel();
                        exam.setId(cur.getInt(cur.getColumnIndex(ID)));
                        exam.setName(cur.getString(cur.getColumnIndex(NAME)));
                        exam.setLocation(cur.getString(cur.getColumnIndex(LOCATION)));
                        exam.setTime(cur.getString(cur.getColumnIndex(TIME)));
                        exam.setDetail(cur.getString(cur.getColumnIndex(DETAILS)));
                        examList.add(exam);
                    }
                    while(cur.moveToNext());
                }
            }
        }
        finally {
            db.endTransaction();
            assert cur != null;
            cur.close();
        }
        return examList;
    }

//    public void updateName(int id, String name){
//        ContentValues cv = new ContentValues();
//        cv.put(NAME, name);
//        db.update(EXAM_TABLE, cv, ID + "= ?", new String[] {String.valueOf(id)});
//    }
//
//    public void updateLocation(int id, String location) {
//        ContentValues cv = new ContentValues();
//        cv.put(LOCATION, location);
//        db.update(EXAM_TABLE, cv, ID + "= ?", new String[] {String.valueOf(id)});
//    }
//
//    public void updateTime(int id, String time) {
//        ContentValues cv = new ContentValues();
//        cv.put(TIME, time);
//        db.update(EXAM_TABLE, cv, ID + "= ?", new String[] {String.valueOf(id)});
//    }
//
//    public void updateDetail(int id, String detail) {
//        ContentValues cv = new ContentValues();
//        cv.put(DETAILS, detail);
//        db.update(EXAM_TABLE, cv, ID + "= ?", new String[] {String.valueOf(id)});
//    }

    public void updateExam(ExamModel exam) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(NAME, exam.getName());
        cv.put(LOCATION, exam.getLocation());
        cv.put(TIME, exam.getTime());
        cv.put(DETAILS, exam.getDetail());
        db.update(EXAM_TABLE, cv, ID + "= ?", new String[] {String.valueOf(exam.getId())});


    }

    public void closeDatabase() {
        if (db != null && db.isOpen()) {
            db.close();
        }
    }

    public void deleteExam(int id){
        db.delete(EXAM_TABLE, ID + "= ?", new String[] {String.valueOf(id)});
        db.close();
    }
}