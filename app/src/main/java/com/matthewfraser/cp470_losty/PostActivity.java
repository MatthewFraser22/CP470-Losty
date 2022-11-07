package com.matthewfraser.cp470_losty;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class PostActivity extends AppCompatActivity {
    protected static String TAG = "PostActivity";
    private final int REQUEST_CODE = 123;

    EditText itemNameEditText, brandNameEditText, colorEditText,
            descriptionEditText, otherEditText;
    ImageButton imageButton;
    Button postButton;
    PostDatabaseHelper postDatabase;
    String lostItemPhotoString = "";

    // Progress bar
    ProgressDialog progressBar;
    private int progressBarStatus = 0;
    private Handler progressHandler = new Handler();

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

                    progressBar = new ProgressDialog(v.getContext());
                    progressBar.setCancelable(true);
                    progressBar.setMessage("Posting...");
                    progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    progressBar.setProgress(0);
                    progressBar.setMax(100);
                    progressBar.show();

                    progressBarStatus = 0;

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while (progressBarStatus < 100) {
                                progressBarStatus += 1;

                                try {
                                    Thread.sleep(100);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                                progressHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressBar.setProgress(progressBarStatus);
                                    }
                                });

                                progressBar.setProgress(progressBarStatus);

                                if (progressBarStatus >= 100 && insertData == true) {
                                    Log.i("TAG", "TESTING - data saved");
                                    progressBar.dismiss();
                                } else if (progressBarStatus >= 50 && insertData == false) {
                                    Log.i("TAG", "TESTING - data NOT saved");
                                    progressBar.dismiss();
                                }

                                progressBar.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                    @Override
                                    public void onDismiss(DialogInterface dialog) {
                                        finish();
                                        String toastText = "";

                                        if (insertData == true) {
                                            toastText = "Successfully uploaded post";
                                        } else {
                                            toastText = "Error: inserting data - data not saved";
                                        }

                                        Toast.makeText(PostActivity.this, toastText, Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    }).start();
                }
            }
        });
    }

    private Boolean isPostValid() {
        Boolean name = itemNameEditText.getText().toString().isEmpty();
        Boolean brand = brandNameEditText.getText().toString().isEmpty();
        Boolean color = colorEditText.getText().toString().isEmpty();
        Boolean description = descriptionEditText.getText().toString().isEmpty();

        Boolean isValid = name == false && brand == false && color == false && description == false && lostItemPhotoString.isEmpty() == false;
        
        if (isValid == true) {
            Log.i(TAG, "Post is valid");
            return true;
        } else {
            Snackbar.make(getWindow().getDecorView().findViewById(R.id.postActivity), "Please fill out all entries", Snackbar.LENGTH_LONG).show();
            Log.i(TAG, "Post is NOT valid");
            return false;
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