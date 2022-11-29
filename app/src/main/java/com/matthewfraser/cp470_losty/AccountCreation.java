package com.matthewfraser.cp470_losty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLException;

public class AccountCreation extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "AccountCreation";
    private Button createAccount;
    private EditText editName;
    private EditText editEmail;
    private EditText editPhone;
    private EditText editUsername;
    private EditText editPassword;
    private boolean uniqueUsername;
    private int ctr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_creation);
        editName = (EditText)findViewById(R.id.enterAccountName);
        editEmail = (EditText)findViewById(R.id.enterAccountEmail);
        editPhone = (EditText)findViewById(R.id.enterAccountPhoneNumber);
        editUsername = (EditText)findViewById(R.id.enterAccountUsername);
        editPassword = (EditText)findViewById(R.id.enterAccountPassword);
        createAccount = (Button)findViewById(R.id.accountCreationButton);
        DBHandler dbHandler = new DBHandler(AccountCreation.this);
        ctr = dbHandler.readAccounts().size() + 1;


        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editName.getText().toString();
                String email = editEmail.getText().toString();
                String phone = editPhone.getText().toString();
                String username = editUsername.getText().toString();
                String password = editPassword.getText().toString();

                if (name.isEmpty() && email.isEmpty() && username.isEmpty() && password.isEmpty()) {
                    Toast.makeText(AccountCreation.this, "Please Fill in missing information", Toast.LENGTH_SHORT).show();
                    return;
                }


                if(name.length() != 0 && email.length() != 0 && username.length() != 0 && password.length() != 0) {
                    if(email.contains("@") && email.contains(".")) {
                        if(password.length() >= 8) {
                            if(password.matches(".*\\d.*")) {
                                uniqueUsername = dbHandler.addNewAccount(name, email, phone, username, password);
                                if (uniqueUsername) {
                                    Toast.makeText(AccountCreation.this, "Account has been created", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(AccountCreation.this, Login.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(AccountCreation.this, "Username is already taken", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(AccountCreation.this, "Password must contain a number.", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(AccountCreation.this, "Password must be 8 characters or longer", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(AccountCreation.this, "Email must contain '@' and a '.'", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });

    }
}