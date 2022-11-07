package com.matthewfraser.cp470_losty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AccountCreation extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "AccountCreation";
    private Button createAccount;
    private EditText editName;
    private EditText editEmail;
    private EditText editUsername;
    private EditText editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_creation);
        editName = (EditText)findViewById(R.id.enterAccountName);
        editEmail = (EditText)findViewById(R.id.enterAccountEmail);
        editUsername = (EditText)findViewById(R.id.enterAccountUsername);
        editPassword = (EditText)findViewById(R.id.enterAccountPassword);
        createAccount = (Button)findViewById(R.id.accountCreationButton);
        DBHandler dbHandler = new DBHandler(AccountCreation.this);

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editName.getText().toString();
                String email = editEmail.getText().toString();
                String username = editUsername.getText().toString();
                String password = editPassword.getText().toString();

                if (name.isEmpty() && email.isEmpty() && username.isEmpty() && password.isEmpty()) {
                    Toast.makeText(AccountCreation.this, "Please Fill in missing information", Toast.LENGTH_SHORT).show();
                    return;
                }


                if(name.length() != 0 && email.length() != 0 && username.length() != 0 && password.length() != 0) {

                    dbHandler.addNewAccount(name, email, username, password);

                    Toast.makeText(AccountCreation.this, "Account has been created", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(AccountCreation.this, MainActivity.class);
                    startActivity(intent);
                }

            }
        });

    }
}