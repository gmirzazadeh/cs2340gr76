package com.example.cs2340gr76.ui.assignments.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.cs2340gr76.ui.assignments.Model.AssignmentsModel;

import java.util.ArrayList;
import java.util.List;

public class AssignmentsDataHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "assignments_db";
    private static final String ASSIGNMENTS_TABLE = "assignments";
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String CLASSNAME = "classname";
    private static final String DATE = "date";
    private static final String TIMESTAMP = "timestamp";
    private static final String CREATE_ASSIGNMENTS_TABLE = "CREATE TABLE " + ASSIGNMENTS_TABLE + "(" +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TITLE + " TEXT, " +
            CLASSNAME + " TEXT, " +
            DATE + " TEXT, " +
            TIMESTAMP + " TEXT)";

    private SQLiteDatabase db;

    public AssignmentsDataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the assignments table
        db.execSQL(AssignmentsModel.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if it exists and recreate the table
        db.execSQL("DROP TABLE IF EXISTS " + AssignmentsModel.TABLE_NAME);
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
    // Insert a new assignment
    public long insertAssignment(AssignmentsModel assignment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(AssignmentsModel.COLUMN_TITLE, assignment.getTitle());
        values.put(AssignmentsModel.COLUMN_CLASSNAME, assignment.getClassName());
        values.put(AssignmentsModel.COLUMN_DATE, assignment.getDate());
        values.put(AssignmentsModel.COLUMN_TIMESTAMP, assignment.getTime());

        long id = db.insert(AssignmentsModel.TABLE_NAME, null, values);
        db.close();
        return id;
    }

    public AssignmentsModel getAssignment(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                AssignmentsModel.TABLE_NAME,
                new String[]{AssignmentsModel.COLUMN_ID, AssignmentsModel.COLUMN_TITLE,
                        AssignmentsModel.COLUMN_CLASSNAME, AssignmentsModel.COLUMN_DATE,
                        AssignmentsModel.COLUMN_TIMESTAMP},
                AssignmentsModel.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)},
                null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();

            AssignmentsModel assignment = new AssignmentsModel(
                    cursor.getString(cursor.getColumnIndex(AssignmentsModel.COLUMN_TITLE)),
                    cursor.getString(cursor.getColumnIndex(AssignmentsModel.COLUMN_DATE)),
                    cursor.getString(cursor.getColumnIndex(AssignmentsModel.COLUMN_TIMESTAMP)),
                    cursor.getString(cursor.getColumnIndex(AssignmentsModel.COLUMN_CLASSNAME)),
                    null);

            cursor.close();
            return assignment;
        }

        return null;
    }

    // Get all assignments
    public List<AssignmentsModel> getAllAssignments() {
        List<AssignmentsModel> assignmentsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                AssignmentsModel.TABLE_NAME,
                new String[]{AssignmentsModel.COLUMN_ID, AssignmentsModel.COLUMN_TITLE,
                        AssignmentsModel.COLUMN_CLASSNAME, AssignmentsModel.COLUMN_DATE,
                        AssignmentsModel.COLUMN_TIMESTAMP},
                null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                AssignmentsModel assignment = new AssignmentsModel(
                        cursor.getString(cursor.getColumnIndex(AssignmentsModel.COLUMN_TITLE)),
                        cursor.getString(cursor.getColumnIndex(AssignmentsModel.COLUMN_DATE)),
                        cursor.getString(cursor.getColumnIndex(AssignmentsModel.COLUMN_TIMESTAMP)),
                        cursor.getString(cursor.getColumnIndex(AssignmentsModel.COLUMN_CLASSNAME)),
                        null);
                assignment.setId(cursor.getInt(cursor.getColumnIndex(AssignmentsModel.COLUMN_ID)));
                assignmentsList.add(assignment);
            } while (cursor.moveToNext());

            cursor.close();
        }

        return assignmentsList;
    }

    // Update an assignment
    public int updateAssignment(AssignmentsModel assignment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(AssignmentsModel.COLUMN_TITLE, assignment.getTitle());
        values.put(AssignmentsModel.COLUMN_CLASSNAME, assignment.getClassName());
        values.put(AssignmentsModel.COLUMN_DATE, assignment.getDate());
        values.put(AssignmentsModel.COLUMN_TIMESTAMP, assignment.getTime());

        return db.update(AssignmentsModel.TABLE_NAME, values, AssignmentsModel.COLUMN_ID + " = ?",
                new String[]{String.valueOf(assignment.getId())});
    }

    // Delete an assignment
    public void deleteAssignment(AssignmentsModel assignment) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(AssignmentsModel.TABLE_NAME, AssignmentsModel.COLUMN_ID + " = ?",
                new String[]{String.valueOf(assignment.getId())});
        db.close();
    }
}
