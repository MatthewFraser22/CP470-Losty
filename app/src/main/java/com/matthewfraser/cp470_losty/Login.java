package com.matthewfraser.cp470_losty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class Login extends AppCompatActivity {
    private Button createAccount;
    private Button login;
    private EditText editUsername;
    private EditText editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editUsername = (EditText)findViewById(R.id.enterLoginUsername);
        editPassword = (EditText)findViewById(R.id.enterLoginPassword);
        createAccount = (Button)findViewById(R.id.accountCreationButton);
        login = (Button)findViewById(R.id.loginButton);

        DBHandler db = new DBHandler(Login.this);

        ArrayList<AccountModel> accounts = db.readAccounts();

        for(int i = 0; i < accounts.size(); i++) {
            System.out.println(accounts.get(i).getUsername());
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = editUsername.getText().toString();
                String password = editPassword.getText().toString();
                for(int i = 0; i < accounts.size(); i++) {
                    if(accounts.get(i).getUsername().equals(username)) {
                        if(accounts.get(i).getPassword().equals(password)) {
                            // Set session var to include user ID upon login
                            SharedPreferences preferences = getSharedPreferences("losty", Context.MODE_PRIVATE);
                            preferences.edit().putInt("userId", accounts.get(i).getId()).commit();
                            preferences.edit().putString("email", accounts.get(i).getEmail()).commit();
                            preferences.edit().putString("name", accounts.get(i).getName()).commit();
                            preferences.edit().putString("username", accounts.get(i).getUsername()).commit();
                            Intent intent = new Intent(Login.this, ProfileActivity.class);
                            startActivity(intent);
                        }
                    }
                }

            }
        });


        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, AccountCreation.class);
                startActivity(intent);
            }
        });

    }
}