package com.matthewfraser.cp470_losty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.matthewfraser.cp470_losty.databinding.ActivityHelpBinding;

public class HelpActivity extends AppCompatActivity {

    Button  post, feed, sign, author;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Help Page");
        setContentView(R.layout.activity_help);



        post = findViewById(R.id.post);
        feed = findViewById(R.id.feed);
        sign = findViewById(R.id.sign);
        author = findViewById(R.id.author);

//
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new HowToPostFragment());
            }
        });



        feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new ViewFeedFragment());

            }
        });

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new SignInOutFragment());
            }
        });

        author.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(HelpActivity.this);
                builder.setTitle("Version 1.0, by Matthew Fraser, Mitchell Newson, Alex Do, Owen Read");
                builder.setCancelable(false);
                builder.setPositiveButton("Back", (DialogInterface.OnClickListener) (dialog, which) -> {
                    dialog.cancel();
                });



                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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

                return false;
            }
        });
//        binding = ActivityHelpBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//        binding.bottomNavigation.setOnItemSelectedListener(item -> {
//            switch (item.getItemId()) {
//                case R.id.action_one:
//
//                    break;
//                case R.id.action_two:
//
//                    startActivity(goPost);
//                    break;
//                case R.id.action_three:
//
//                    break;
//                case R.id.action_four:
//
//                    break;
//            }
//            return true;
//        });


    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }
}
