package com.matthewfraser.cp470_losty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.matthewfraser.cp470_losty.databinding.ActivityHelpBinding;

public class HelpActivity extends AppCompatActivity {


    ActivityHelpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_help);
        binding = ActivityHelpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_one:

                    break;
                case R.id.action_two:
                    Intent post = new Intent(HelpActivity.this, PostActivity.class);
                    startActivity(post);
                    break;
                case R.id.action_three:

                    break;
                case R.id.action_four:

                    break;
            }
            return true;
        });
    }
}
