package com.matthewfraser.cp470_losty;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        return true;
    }

//    public boolean onOptionsItemSelected(MenuItem mi) {
//        switch (mi.getItemId()){
//            case R.id.action_one:
//                Log.d("Toolbar", "Option 1 selected");
//                Intent home = new Intent(HelpPage.this, HomeActivity.class);
//                startActivity(home);
//
//                break;
//            case R.id.action_two:
//                Log.d("Toolbar", "Option 2 selected");
//                Intent post = new Intent(HelpPage.this, PostActivity.class);
//                startActivity(post);
//                break;
//            case R.id.action_three:
//                Log.d("Toolbar", "Option 3 selected");
//                Intent profile = new Intent(HelpPage.this, ProfileActivity.class);
//                startActivity(profile);
//                break;
//            case R.id.action_four:
//
//                Intent help = new Intent(HelpPage.this, PostActivity.class);
//                startActivity(help);
//
//        }
//        return true;
//    }
}
