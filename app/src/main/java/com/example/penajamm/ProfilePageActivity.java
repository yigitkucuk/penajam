package com.example.penajamm;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.penajamm.databinding.ActivityProfilePageBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ProfilePageActivity extends AppCompatActivity {

    private ActivityProfilePageBinding binding;
    private ImageButton btnSettings, btnMainScreen, btnAssig, btnBack;
    private VideoView videoView;
    private TextView textView;
    private ArrayList<User> list;

    FirebaseAuth auth;
    FirebaseUser user;

    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference usersRef = rootRef.child("User");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityProfilePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);

        Userbase userbase = new Userbase();

        list = userbase.getUsers();
        for (User u: list) {
            System.out.println(u.getName());
        }

        FloatingActionButton fab = binding.fab;
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

        btnSettings = findViewById(R.id.btn_Settings);
        btnMainScreen = findViewById(R.id.btn_MainScreen);
        btnAssig = findViewById(R.id.btn_Assig);
        btnBack = findViewById(R.id.backbtn);
        videoView = findViewById(R.id.videoView);
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.video;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        User currentUser = userbase.getSpecificUser(user.getEmail());
        textView = (TextView) findViewById(R.id.name);

        if (currentUser != null)
            textView.setText(currentUser.getRealname());
        else
            textView.setText(user.getEmail());


        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);

        btnBack.setOnClickListener(view -> goMainScreen());

        btnSettings.setOnClickListener(view -> goSettings());

        btnMainScreen.setOnClickListener(view -> goMainScreen());

        btnAssig.setOnClickListener(view -> goAssig());
    }

    public void goSettings() {
        startActivity(new Intent(ProfilePageActivity.this, SettingsActivity.class));
    }

    public void goMainScreen() {
        startActivity(new Intent(ProfilePageActivity.this, MainScreenActivity.class));
    }

    public void goAssig() {
        startActivity(new Intent(ProfilePageActivity.this, ScrollingActivity.class));
    }

}