package com.matthewfraser.cp470_losty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemProfile extends AppCompatActivity {

    PostDatabaseHelper postDB;
    DBHandler userDB;

    TextView postTitle;
    TextView postBrand;
    TextView postColor;
    TextView postDesc;
    ImageView postImage;

    TextView userName;
    TextView userEmail;

    Button phoneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_profile);

       postTitle = findViewById(R.id.ItemProfileTitle);
       postBrand = findViewById(R.id.ItemProfileBrand);
       postColor = findViewById(R.id.ItemProfileColor);
       postDesc = findViewById(R.id.ItemProfileDesc);
       postImage = findViewById(R.id.ItemProfileImage);
       userName = findViewById(R.id.UserProfileName);
       userEmail = findViewById(R.id.UserProfileEmail);
       phoneButton = findViewById(R.id.PhoneButton);

       Bundle extras = getIntent().getExtras();

       postDB = new PostDatabaseHelper(ItemProfile.this);
       userDB = new DBHandler(ItemProfile.this);

       GetData(extras.getString("UserID"), extras.getString("PostID"));
       getSupportActionBar().setTitle(getString(R.string.item_profile_title));
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);

       phoneButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(Intent.ACTION_DIAL);
               intent.setData(Uri.parse("tel:" + phoneButton.getText().toString()));
               startActivity(intent);
           }
       });
    }

    private void GetData(String userID, String postID) {

        SQLiteDatabase db = userDB.getWritableDatabase();
        Cursor userCursor = db.rawQuery("SELECT * FROM accounts WHERE id='" + userID + "'", null);
        if (userCursor.moveToFirst()) {
            do {
                userName.setText(getString(R.string.name) + " " + userCursor.getString(3));
                userEmail.setText(getString(R.string.email) + " " + userCursor.getString(2));
                // SET BUTTON TO PHONE NUMBER BUTTTON HERE!!!
            } while (userCursor.moveToNext());
        }
        userCursor.close();

        SQLiteDatabase db2 = postDB.getWritableDatabase();
        Cursor postCursor = db2.rawQuery("SELECT * FROM " + PostDatabaseHelper.TABLE_NAME + " WHERE ID='" + postID + "'", null);
        if (postCursor.moveToFirst()) {
            do {
                postTitle.setText(postCursor.getString(2));
                postBrand.setText(postCursor.getString(3));
                postColor.setText(postCursor.getString(4));
                postDesc.setText(postCursor.getString(5));
                postImage.setImageBitmap(stringToBitmap(postCursor.getString(1)));
            } while (postCursor.moveToNext());
        }
        postCursor.close();
    }

    private Bitmap stringToBitmap(String str) {
        Uri uri = Uri.parse(str);
        try {
            return  MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
        } catch (Exception e) {
            e.getMessage();
            return null;
        }

    }


}