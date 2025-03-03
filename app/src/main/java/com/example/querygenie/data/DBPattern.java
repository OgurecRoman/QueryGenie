package com.example.querygenie.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.querygenie.data.model.PatternModel;

import java.util.ArrayList;

public class DBPattern {
    private static final String DATABASE_NAME = "querygenie.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_PATTERNS = "tablePatterns";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "Name";
    private static final String COLUMN_ROLE = "Role";
    private static final String COLUMN_GOAL = "Goal";
    private static final String COLUMN_ENVIRONMENT = "Environment";
    private static final String COLUMN_ISLIKED = "Isliked";

    private static final int NUM_COLUMN_ID = 0;
    private static final int NUM_COLUMN_NAME = 1;
    private static final int NUM_COLUMN_ROLE = 2;
    private static final int NUM_COLUMN_GOAL = 3;
    private static final int NUM_COLUMN_ENVIRONMENT = 4;
    private static final int NUM_COLUMN_ISLIKED = 5;

    private SQLiteDatabase mDataBase;

    public DBPattern(Context context) {
        OpenHelper mOpenHelper = new OpenHelper(context);
        mDataBase = mOpenHelper.getWritableDatabase();
    }

    public long insert(String name, String role, String goal, String environment, boolean isliked) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_ROLE, role);
        cv.put(COLUMN_GOAL, goal);
        cv.put(COLUMN_ENVIRONMENT, environment);
        cv.put(COLUMN_ISLIKED, isliked);
        return mDataBase.insert(TABLE_PATTERNS, null, cv);
    }

    public int update(PatternModel md) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, md.getName());
        cv.put(COLUMN_ROLE, md.getRole());
        cv.put(COLUMN_GOAL, md.getGoal());
        cv.put(COLUMN_ENVIRONMENT, md.getEnvironment());
        cv.put(COLUMN_ISLIKED, md.getIsliked());
        return mDataBase.update(TABLE_PATTERNS, cv, COLUMN_ID + " = ?", new String[]{String.valueOf(md.getId())});
    }

    public void deleteAll() {
        mDataBase.delete(TABLE_PATTERNS, null, null);
    }

    public void delete(int id) {
        mDataBase.delete(TABLE_PATTERNS, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public PatternModel select(int id) {
        Cursor mCursor = mDataBase.query(TABLE_PATTERNS, null, COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)}, null, null, null);

        mCursor.moveToFirst();
        String name = mCursor.getString(NUM_COLUMN_NAME);
        String role = mCursor.getString(NUM_COLUMN_ROLE);
        String goal = mCursor.getString(NUM_COLUMN_GOAL);
        String environment = mCursor.getString(NUM_COLUMN_ENVIRONMENT);
        boolean isliked = mCursor.getInt(NUM_COLUMN_ISLIKED) > 0;
        return new PatternModel(id, name, role, goal, environment, isliked);
    }

    public ArrayList<PatternModel> selectAll() {
        Cursor mCursor = mDataBase.query(TABLE_PATTERNS, null, null,
                null, null, null, null);

        ArrayList<PatternModel> arr = new ArrayList<PatternModel>();
        mCursor.moveToFirst();
        if (!mCursor.isAfterLast()) {
            do {
                int id = mCursor.getInt(NUM_COLUMN_ID);
                String name = mCursor.getString(NUM_COLUMN_NAME);
                String role = mCursor.getString(NUM_COLUMN_ROLE);
                String goal = mCursor.getString(NUM_COLUMN_GOAL);
                String environment = mCursor.getString(NUM_COLUMN_ENVIRONMENT);
                boolean isliked = mCursor.getInt(NUM_COLUMN_ISLIKED) > 0;
                arr.add(new PatternModel(id, name, role, goal, environment, isliked));
            } while (mCursor.moveToNext());
        }
        return arr;
    }

    public ArrayList<PatternModel> filterLike(String line) {
        ArrayList<PatternModel> arr = new ArrayList<PatternModel>();

        String query = "SELECT * FROM " + TABLE_PATTERNS + " WHERE " + COLUMN_NAME + " LIKE ?";
        String[] selectionArgs = new String[]{"%" + line + "%"};

        Cursor mCursor = mDataBase.rawQuery(query, selectionArgs);

        mCursor.moveToFirst();
        if (!mCursor.isAfterLast()) {
            do {
                int id = mCursor.getInt(NUM_COLUMN_ID);
                String name = mCursor.getString(NUM_COLUMN_NAME);
                String role = mCursor.getString(NUM_COLUMN_ROLE);
                String goal = mCursor.getString(NUM_COLUMN_GOAL);
                String environment = mCursor.getString(NUM_COLUMN_ENVIRONMENT);
                boolean isliked = mCursor.getInt(NUM_COLUMN_ISLIKED) > 0;
                arr.add(new PatternModel(id, name, role, goal, environment, isliked));
            } while (mCursor.moveToNext());
        }
        return arr;
    }

    private class OpenHelper extends SQLiteOpenHelper {

        OpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String query = "CREATE TABLE " + TABLE_PATTERNS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_ROLE + " TEXT, " +
                    COLUMN_GOAL + " TEXT," +
                    COLUMN_ENVIRONMENT + " TEXT," +
                    COLUMN_ISLIKED + " BOOLEAN);";
            db.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_PATTERNS);
            onCreate(db);
        }
    }

}
