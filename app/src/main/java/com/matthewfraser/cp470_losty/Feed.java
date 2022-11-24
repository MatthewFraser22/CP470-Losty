package com.matthewfraser.cp470_losty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;

import java.util.ArrayList;
import java.util.List;

public class Feed extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    List<ItemModel> itemList;
    ItemAdapter adapter;
    PostDatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        adapter.notifyDataSetChanged();

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