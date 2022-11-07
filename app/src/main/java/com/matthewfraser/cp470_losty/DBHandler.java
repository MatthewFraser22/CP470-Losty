package com.matthewfraser.cp470_losty;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "accountDB";

    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "accounts";
    private static final String ID_COL = "id";
    private static final String NAME_COL = "name";
    private static final String EMAIL_COL = "email";
    private static final String USERNAME_COL = "username";
    private static final String PASSWORD_COL = "password";


    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT,"
                + EMAIL_COL + " TEXT,"
                + USERNAME_COL + " TEXT,"
                + PASSWORD_COL + " TEXT)";

        db.execSQL(query);
    }

    public void addNewAccount(String name, String email, String username, String password) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(NAME_COL, name);
        values.put(EMAIL_COL, email);
        values.put(USERNAME_COL, username);
        values.put(PASSWORD_COL, password);

        db.insert(TABLE_NAME, null, values);

        db.close();

    }


    public ArrayList<AccountModel> readAccounts() {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursorAccounts = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        ArrayList<AccountModel> accountModalArrayList = new ArrayList<>();

        if (cursorAccounts.moveToFirst()) {
            do {
                accountModalArrayList.add(new AccountModel(cursorAccounts.getString(1),
                        cursorAccounts.getString(2),
                        cursorAccounts.getString(3),
                        cursorAccounts.getString(4)));
            } while (cursorAccounts.moveToNext());
        }
        cursorAccounts.close();
        return accountModalArrayList;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}