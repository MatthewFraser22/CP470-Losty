package com.matthewfraser.cp470_losty;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class PostActivity extends AppCompatActivity {
    protected static String TAG = "PostActivity";
    private final int REQUEST_CODE = 123;

    EditText itemNameEditText, brandNameEditText, colorEditText,
            descriptionEditText, otherEditText;
    ImageButton imageButton;
    Button postButton;
    PostDatabaseHelper postDatabase;
    String lostItemPhotoString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        itemNameEditText = findViewById(R.id.lost_item_edit_text);
        brandNameEditText = findViewById(R.id.lost_item_brand_edit_text);
        colorEditText = findViewById(R.id.lost_item_color_edit_text);
        descriptionEditText = findViewById(R.id.lost_item_description_edit_text);
        otherEditText = findViewById(R.id.lost_item_other_edit_text);
        postButton = findViewById(R.id.postButton);
        imageButton = findViewById(R.id.imageButton);

        postDatabase = new PostDatabaseHelper(this);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoGalleryintent = new Intent();
                photoGalleryintent.setType("image/*");
                photoGalleryintent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(photoGalleryintent, "Select Picture"), REQUEST_CODE);
            }
        });

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean isPostValid = isPostValid();

                if (isPostValid == true) {
                    String name = itemNameEditText.getText().toString();
                    String brand = brandNameEditText.getText().toString();
                    String color = colorEditText.getText().toString();
                    String description = descriptionEditText.getText().toString();
                    String other = otherEditText.getText().toString();

                    Boolean insertData = postDatabase.addData(lostItemPhotoString, name, brand, color, description, other);

                    if (insertData == true) {
                        Log.i("TAG", "TESTING - data saved");

                        // 2. display a loading symbol
                        // 3. Save Display a snackbar saying the post was completed successfully
                        // 4. close the activity and move to the post "main page"
                    } else {
                        Log.i("TAG", "TESTING - data NOT saved");
                        // snack or toast showing error
                    }

                }
            }
        });
    }

    private Boolean isPostValid() {
        Boolean name = itemNameEditText.getText().toString().isEmpty();
        Boolean brand = brandNameEditText.getText().toString().isEmpty();
        Boolean color = colorEditText.getText().toString().isEmpty();
        Boolean description = descriptionEditText.getText().toString().isEmpty();

        Boolean isFieldEmpty = name && brand && color && description && lostItemPhotoString.isEmpty() == false;

        if (isFieldEmpty == true) {

            // TODO - Custom alert dialog box
            Log.i(TAG, "Post is NOT valid");
            return false;
        } else {
            Log.i(TAG, "Post is valid");
            return true;
        }
    }

    // TESTING Diplay
    public void display(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE) {
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    lostItemPhotoString = selectedImageUri.toString();
                    imageButton.setImageURI(selectedImageUri);
                }
            }
        }
    }
}