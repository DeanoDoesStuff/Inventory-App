package com.zybooks.project1;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class InventoryDatabase extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "inventory.db";
    private static final int DATABASE_VERSION = 1;

    // Table creation SQL
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + InventoryDatabase.InventoryData.TABLE + " (" +
                    InventoryDatabase.InventoryData._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    InventoryDatabase.InventoryData.COLUMN_ITEM_NAME + " TEXT NOT NULL, " +
                    InventoryDatabase.InventoryData.COLUMN_ITEM_QUANTITY + " TEXT NOT NULL)";

    // Table deletion SQL
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + InventoryDatabase.InventoryData.TABLE;

    public InventoryDatabase(Context context) {
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
    public static class InventoryData {
        public static final String TABLE = "Inventory"; // Table name
        public static final String _ID = "_id"; // Primary key
        public static final String COLUMN_ITEM_NAME = "item"; // Item name column item
        public static final String COLUMN_ITEM_QUANTITY = "quantity"; // Quantity column
    }

    public void insertItem(String name, int quantity) {
        // Get writable database
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(InventoryData.COLUMN_ITEM_NAME, name);
        values.put(InventoryData.COLUMN_ITEM_QUANTITY, quantity);

        // Insert the new row, returning the primary key value of the new row
        db.insert(InventoryData.TABLE, null, values);
    }

    public void deleteItem(String name) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Define 'where' part of the query.
        String selection = InventoryData.COLUMN_ITEM_NAME + " = ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = { name };

        // Execute delete operation
        db.delete(InventoryData.TABLE, selection, selectionArgs);
    }
}
