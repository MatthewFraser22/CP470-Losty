package com.matthewfraser.cp470_losty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class Feed extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    List<ItemModel> itemList;
    ItemAdapter adapter;
    PostDatabaseHelper db;
    Button post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Home Feed");
        setContentView(R.layout.activity_feed);

        db = new PostDatabaseHelper(Feed.this);
        //setToTestData(74);
        setToDatabaseData();
        recyclerView = findViewById(R.id.FeedRecycler);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ItemAdapter(itemList);
        recyclerView.setAdapter(adapter);
        post = findViewById(R.id.createAPost);
        adapter.notifyDataSetChanged();

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent postIntent = new Intent(getApplicationContext(), PostActivity.class);
                startActivity(postIntent);
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_one:

                        break;
                    case R.id.action_two:
                        Intent post = new Intent(Feed.this, PostActivity.class);
                        startActivity(post);
                        break;
                    case R.id.action_three:
                        Intent profile = new Intent(Feed.this, ProfileActivity.class);
                        startActivity(profile);

                        break;
                    case R.id.action_four:
                        Intent help = new Intent(Feed.this, HelpActivity.class);
                        startActivity(help);

                        break;
                }

                return false;
            }
        });

    }

    /*
    private void setToTestData(int numberOfElements) {
        itemList = new ArrayList<>();
        for (int ctr = 1; ctr <= numberOfElements; ctr++ ) {
            itemList.add(new ItemModel(R.drawable.test_face,
                    "Item " + ctr,
                    "Brand" + ctr,
                    "Color" + ctr,
                    "Description" + ctr));
        }
    }
     */

    private void setToDatabaseData() {
        Cursor cursor = db.showData();
        itemList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                Bitmap image = stringToBitmap(cursor.getString(1));
                itemList.add(new ItemModel(image,
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5)));
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

}