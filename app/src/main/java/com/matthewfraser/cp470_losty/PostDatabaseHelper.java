package com.matthewfraser.cp470_losty;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.ByteArrayOutputStream;

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
    private static final String COL9 = "IMAGEBLOB";
    public Context currentContext;

    public PostDatabaseHelper(Context cxt) {
        super(cxt, DATABASE_NAME, null, DATABASE_VERSION);
        currentContext = cxt;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "PICTURE TEXT, ITEM TEXT, BRAND TEXT, COLOR TEXT, DESCRIPTION TEXT, OTHER TEXT, USERID TEXT, IMAGEBLOB BLOB)";

        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String picture, String item, String brand, String color, String description, String other, String userID, byte[] blob) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL2, picture);
        contentValues.put(COL3, item);
        contentValues.put(COL4, brand);
        contentValues.put(COL5, color);
        contentValues.put(COL6, description);
        contentValues.put(COL7, other);
        contentValues.put(COL8, userID);
        contentValues.put(COL9, blob);

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

    public Cursor showData(String userId) {
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor data = database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE USERID='" + userId + "'", null);

        return data;
    }

    public boolean deletePostById(int id) {
        SQLiteDatabase database = this.getWritableDatabase();
        try {
            database.delete(TABLE_NAME, "id = ?", new String[]{String.valueOf(id)});
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @SuppressLint("Range")
    public byte[] getItemImage(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        byte[] profileImageBlob = null;
        Cursor cursorAccounts = db.rawQuery("SELECT "+ COL9 + " FROM " + TABLE_NAME + " WHERE " + COL1 + "='" + id + "'", null);

        try {
            if(cursorAccounts.getCount() > 0) {
                cursorAccounts.moveToFirst();
                profileImageBlob = cursorAccounts.getBlob(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return profileImageBlob;
    }

}
