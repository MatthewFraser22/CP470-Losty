package com.matthewfraser.cp470_losty;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBHandler db = new DBHandler(MainActivity.this);

        ArrayList<AccountModel> accounts = db.readAccounts();

        for(int j = 0; j < accounts.size(); j++) {
            System.out.println(accounts.get(j).getName());
            System.out.println(accounts.get(j).getEmail());
            System.out.println(accounts.get(j).getUsername());
            System.out.println(accounts.get(j).getPassword());

        }

    }
}