package com.zybooks.project1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LoginDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "login.db";
    private static final int DATABASE_VERSION = 1;

    // Table creation SQL
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + LoginInfo.TABLE + " (" +
                    LoginInfo._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    LoginInfo.COLUMN_USERNAME + " TEXT NOT NULL, " +
                    LoginInfo.COLUMN_PASSWORD + " TEXT NOT NULL)";

    // Table deletion SQL
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + LoginInfo.TABLE;

    public LoginDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    // Inner class that defines the table contents
    public static class LoginInfo {
        public static final String TABLE = "users"; // Table name
        public static final String _ID = "_id"; // Primary key
        public static final String COLUMN_USERNAME = "username"; // Username column
        public static final String COLUMN_PASSWORD = "password"; // Password column
    }
}
