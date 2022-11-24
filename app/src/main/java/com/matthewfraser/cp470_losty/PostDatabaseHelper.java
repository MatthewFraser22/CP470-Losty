package com.matthewfraser.cp470_losty;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PostDatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "post.db";
    public static final String TABLE_NAME = "post_table";
    public static final String COL1 = "ID";
    public static final String COL2 = "PICTURE";
    public static final String COL3 = "ITEM";
    public static final String COL4 = "BRAND";
    public static final String COL5 = "COLOR";
    public static final String COL6 = "DESCRIPTION";
    public static final String COL7 = "OTHER";
    public static final String COL8 = "USERID";

    public PostDatabaseHelper(Context cxt) {
        super(cxt, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "PICTURE TEXT, ITEM TEXT, BRAND TEXT, COLOR TEXT, DESCRIPTION TEXT, OTHER TEXT, USERID TEXT)";

        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String picture, String item, String brand, String color, String description, String other, String userID) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL2, picture);
        contentValues.put(COL3, item);
        contentValues.put(COL4, brand);
        contentValues.put(COL5, color);
        contentValues.put(COL6, description);
        contentValues.put(COL7, other);
        contentValues.put(COL8, userID);

        long result = database.insert(TABLE_NAME, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor showData() {
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor data = database.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        return data;

    }

    public Cursor showUserPostData(String email) {
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor data = database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE email='" + email + "'", null);

        return data;
    }
}
