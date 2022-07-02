package com.example.penajamm;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.penajamm.databinding.ActivityScrollingBinding;

public class ScrollingActivity extends AppCompatActivity {

    private ActivityScrollingBinding binding;
    private ImageButton btnSettings, btnMainScreen;
    private Button btnName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityScrollingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;
        toolBarLayout.setTitle(getTitle());

        FloatingActionButton fab = binding.fab;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        btnSettings = findViewById(R.id.btn_Settings);
        btnMainScreen = findViewById(R.id.btn_MainScreen);
        btnName = findViewById(R.id.btnname);



        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { goSettings(); }
        });

        btnMainScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { goMainScreen(); }
        });

        btnName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { goProfile(); }
        });
    }

    public void goSettings() {
        startActivity(new Intent(ScrollingActivity.this, SettingsActivity.class));
    }

    public void goMainScreen() {
        startActivity(new Intent(ScrollingActivity.this, MainScreenActivity.class));
    }

    public void goProfile() {
        startActivity(new Intent(ScrollingActivity.this, ProfilePageActivity.class));
    }


}