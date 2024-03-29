package com.matthewfraser.cp470_losty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    private Button saveButton;
    private Button signOutButton;
    private EditText emailText;
    private EditText phoneNumberText;
    private EditText nameText;
    private ImageView profilePictureImageView;
    public static final int PICK_IMAGE = 1;
    private RecyclerView postRecyclerView;
    PostDatabaseHelper postDbHandler;
    List<ItemModel> itemList;
    ProfileItemAdapter adapter;
    LinearLayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Profile Page");
        setContentView(R.layout.activity_profile);

        saveButton = (Button)findViewById(R.id.saveButton);
        emailText = (EditText)findViewById(R.id.editTextTextEmailAddress);
        phoneNumberText = (EditText)findViewById(R.id.editTextPhone);
        nameText = (EditText)findViewById(R.id.editTextTextPersonName);
        profilePictureImageView = (ImageView)findViewById(R.id.profilePictureImageView);
        postRecyclerView = (RecyclerView)findViewById(R.id.postRecyclerView);
        DBHandler dbHandler = new DBHandler(ProfileActivity.this);
        postDbHandler= new PostDatabaseHelper(ProfileActivity.this);
        signOutButton = findViewById(R.id.signoutButton);

        // Populate profile picture and edit text fields with data
        byte[] profilePictureBlob = dbHandler.getProfileImage();
        SharedPreferences preferences = getSharedPreferences("losty", Context.MODE_PRIVATE);
        String id = preferences.getString("userId", "");
        String email = preferences.getString("email", "");
        String name = preferences.getString("name", "");
        String phoneNumber = preferences.getString("phone", "");

        if (profilePictureBlob != null) {
            try {
                profilePictureImageView.setImageBitmap(BitmapFactory.decodeByteArray(profilePictureBlob,0, profilePictureBlob.length));
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(ProfileActivity.this, "Failed to fetch profile image.", Toast.LENGTH_LONG).show();
            }

        }
        emailText.setText(email, TextView.BufferType.EDITABLE);
        nameText.setText(name, TextView.BufferType.EDITABLE);
        phoneNumberText.setText(phoneNumber, TextView.BufferType.EDITABLE);

        // Set on click listeners
        profilePictureImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);


            }

        });

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signoutIntent = new Intent(getApplicationContext(), Login.class);
                startActivity(signoutIntent);
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

                // Validate and save value of email if not empty and if it is different from current email
                SharedPreferences preferences = getSharedPreferences("losty", Context.MODE_PRIVATE);
                String email = preferences.getString("email", "");
                if (!emailStr.isEmpty() && !emailStr.equals(email)) {
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

        // Populate user's posts
        // Populate posts list
        setToDatabaseData(id);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        postRecyclerView.setLayoutManager(layoutManager);
        adapter = new ProfileItemAdapter(itemList);
        postRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_one:
                        Intent home = new Intent(ProfileActivity.this, Feed.class);
                        startActivity(home);

                        break;
                    case R.id.action_two:
                        Intent post = new Intent(ProfileActivity.this, PostActivity.class);
                        startActivity(post);


                        break;
                    case R.id.action_three:


                        break;
                    case R.id.action_four:
                        Intent help = new Intent(ProfileActivity.this, HelpActivity.class);
                        startActivity(help);
                        break;
                }

                return false;
            }
        });


    }

    private void setToDatabaseData(String userId) {
        Cursor cursor = postDbHandler.showData(userId);
        itemList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                Bitmap image = stringToBitmap(cursor.getString(1));
                itemList.add(new ItemModel(image,
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getInt(0)));
            } while (cursor.moveToNext());
        }

        cursor.close();
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
//                Toast.makeText(ProfileActivity.this, "Failed to process image", Toast.LENGTH_LONG).show();
            }
        }
    }
}