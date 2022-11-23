package com.matthewfraser.cp470_losty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity {

    private Button saveButton;
    private EditText emailText;
    private EditText phoneNumberText;
    private EditText nameText;
    private ImageView profilePictureImageView;
    public static final int PICK_IMAGE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        saveButton = (Button)findViewById(R.id.saveButton);
        emailText = (EditText)findViewById(R.id.editTextTextEmailAddress);
        phoneNumberText = (EditText)findViewById(R.id.editTextPhone);
        nameText = (EditText)findViewById(R.id.editTextTextPersonName);
        profilePictureImageView = (ImageView)findViewById(R.id.profilePictureImageView);
        DBHandler dbHandler = new DBHandler(ProfileActivity.this);

        byte[] profilePictureBlob = dbHandler.getProfileImage();

        if (profilePictureBlob != null) {
            try {
                profilePictureImageView.setImageBitmap(BitmapFactory.decodeByteArray(profilePictureBlob,0, profilePictureBlob.length));
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(ProfileActivity.this, "Failed to fetch profile image.", Toast.LENGTH_LONG).show();
            }

        }




        profilePictureImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);


            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailStr = emailText.getText().toString();
                String phoneNumberStr = phoneNumberText.getText().toString();
                String nameStr = nameText.getText().toString();
                boolean error = false;
                String successToastStr = "";



                // Validate and save value of email if not empty
                if (!emailStr.isEmpty()) {
                    if(emailStr.contains("@") && emailStr.contains(".")) {
                        // Check if email exists already
                        if (dbHandler.checkIfEmailExists(emailStr) == false) {
                            boolean didUpdateEmail = dbHandler.updateEmail(emailStr);
                            if (didUpdateEmail == false) {
                                error = true;
                            } else {
                                successToastStr += " email";
                            }
                        } else {
                            Toast.makeText(ProfileActivity.this, "Email already taken.", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(ProfileActivity.this, "Email must contain '@' and a '.'", Toast.LENGTH_LONG).show();
                    }

                }

                // Validate name
                if (!nameStr.isEmpty()) {
                    boolean didUpdateName = dbHandler.updateName(nameStr);
                    if (didUpdateName == false) {
                        error = true;
                    } else {
                        successToastStr += " name";
                    }
                }

                if (error) {
                    Toast.makeText(ProfileActivity.this, "Error updating.", Toast.LENGTH_LONG).show();
                } else if (!successToastStr.isEmpty()){
                    Toast.makeText(ProfileActivity.this, "Success! Updated: " + successToastStr, Toast.LENGTH_LONG).show();
                }

            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE) {
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(data.getData()));
                profilePictureImageView.setImageBitmap(bitmap);
                DBHandler dbHandler = new DBHandler(ProfileActivity.this);
                if(dbHandler.updateProfileImage(data.getData().toString())) {
                    Toast.makeText(ProfileActivity.this, "Saved profile image", Toast.LENGTH_LONG).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        this.getContentResolver().takePersistableUriPermission(data.getData(), Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    }

                } else {
                    Toast.makeText(ProfileActivity.this, "Database failed to save image", Toast.LENGTH_LONG).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(ProfileActivity.this, "Failed to process image", Toast.LENGTH_LONG).show();
            }
        }
    }
}