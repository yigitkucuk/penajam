package com.example.penajamm;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.penajamm.databinding.ActivityProfilePageBinding;

public class ProfilePageActivity extends AppCompatActivity {

    private ActivityProfilePageBinding binding;
    private ImageButton btnSettings, btnMainScreen;
    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityProfilePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        //CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;
        //toolBarLayout.setTitle(getTitle());

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
        videoView = findViewById(R.id.videoView);
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.video;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);

        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);



        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { goSettings(); }
        });

        btnMainScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { goMainScreen(); }
        });
    }

    public void goSettings() {
        startActivity(new Intent(ProfilePageActivity.this, SettingsActivity.class));
    }

    public void goMainScreen() {
        startActivity(new Intent(ProfilePageActivity.this, MainScreenActivity.class));
    }
}