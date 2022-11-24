package com.matthewfraser.cp470_losty;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.ByteArrayOutputStream;
import java.sql.SQLException;
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
    private static final String PROFILE_IMAGE_COL = "profile_image";
    private Context currentContext;
    private static final String ID = "ID";
    public static AccountModel model;


    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        currentContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT,"
                + EMAIL_COL + " TEXT,"
                + USERNAME_COL + " TEXT UNIQUE,"
                + PASSWORD_COL + " TEXT,"
                + PROFILE_IMAGE_COL + " BLOB)";

        db.execSQL(query);
    }

    public boolean addNewAccount(String name, String email, String username, String password) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(NAME_COL, name);
        values.put(EMAIL_COL, email);
        values.put(USERNAME_COL, username);
        values.put(PASSWORD_COL, password);

        try {
            db.insertOrThrow(TABLE_NAME, null, values);
            System.out.println("dfhakfkajhgfdlajgas;'gjas;lgjm;agjlm;lg");
        } catch (Throwable ex) {
            System.out.println(ex);
            db.close();
            return false;
        }

        db.close();
        return true;
    }


    public ArrayList<AccountModel> readAccounts() {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursorAccounts = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        ArrayList<AccountModel> accountModalArrayList = new ArrayList<>();

        if (cursorAccounts.moveToFirst()) {
            do {
                model = new AccountModel(cursorAccounts.getString(1),
                        cursorAccounts.getString(2),
                        cursorAccounts.getString(3),
                        cursorAccounts.getString(4),
                        cursorAccounts.getString(0));

                model.setEmail(cursorAccounts.getString(2));
                model.setName(cursorAccounts.getString(1));
                model.setId(cursorAccounts.getString(0));
                model.setPassword(cursorAccounts.getString(3));
                model.setUsername(cursorAccounts.getString(4));
                accountModalArrayList.add(model);

            } while (cursorAccounts.moveToNext());
        }
        cursorAccounts.close();
        return accountModalArrayList;
    }

    public boolean checkIfEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorAccounts = db.rawQuery("SELECT " + EMAIL_COL + " FROM " + TABLE_NAME + " WHERE " + EMAIL_COL + "='" + email + "'", null);
        cursorAccounts.moveToFirst();
        try {
            cursorAccounts.getString(0);
        } catch (CursorIndexOutOfBoundsException e) {
            // Email doesn't exist
            return false;
        }
        return true;
    }

    // Returns true if successful, else false
    public boolean updateEmail(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        SharedPreferences preferences = currentContext.getSharedPreferences("losty", Context.MODE_PRIVATE);
        String oldEmail = preferences.getString("email", "");
        ContentValues cv = new ContentValues();
        cv.put(EMAIL_COL, email);

        try {
            db.update(TABLE_NAME, cv, "email = ?", new String[]{oldEmail});
            preferences.edit().putString("email", email).commit();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean updateName(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        SharedPreferences preferences = currentContext.getSharedPreferences("losty", Context.MODE_PRIVATE);
        int userId = preferences.getInt("userId", -1);
        String email = preferences.getString("email", "");
        ContentValues cv = new ContentValues();
        cv.put(NAME_COL, name);

        try {
            db.update(TABLE_NAME, cv, "email = ?", new String[]{email});
            preferences.edit().putString("name", name).commit();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean updateProfileImage(String uri) {
        SQLiteDatabase db = this.getWritableDatabase();
        SharedPreferences preferences = currentContext.getSharedPreferences("losty", Context.MODE_PRIVATE);
        int userId = preferences.getInt("userId", -1);
        String email = preferences.getString("email", "");

        // Change URI to blob to save to db
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(currentContext.getContentResolver(), Uri.parse(uri));
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
            byte[] blob = stream.toByteArray();

            ContentValues cv = new ContentValues();
            cv.put(PROFILE_IMAGE_COL, blob);

            db.update(TABLE_NAME, cv, EMAIL_COL + " = ?", new String[]{email});
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @SuppressLint("Range")
    public byte[] getProfileImage() {
        SQLiteDatabase db = this.getReadableDatabase();
        SharedPreferences preferences = currentContext.getSharedPreferences("losty", Context.MODE_PRIVATE);
        int userId = preferences.getInt("userId", -1);
        String email = preferences.getString("email", "");
        byte[] profileImageBlob = null;
        Cursor cursorAccounts = db.rawQuery("SELECT "+ PROFILE_IMAGE_COL + " FROM " + TABLE_NAME + " WHERE " + EMAIL_COL + "='" + email + "'", null);

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

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}