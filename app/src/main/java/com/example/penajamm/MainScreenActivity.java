package com.example.penajamm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainScreenActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button btnLogout;
    private ImageButton btnAssig, btnSettings, btnMainScreen, btnProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        mAuth = FirebaseAuth.getInstance();
        btnLogout = findViewById(R.id.btnLogout);
        btnAssig = findViewById(R.id.btn_Assig);
        btnSettings = findViewById(R.id.btn_Settings);
        btnMainScreen = findViewById(R.id.btn_MainScreen);
        btnProfile = findViewById(R.id.btn_Profile);

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { goProfile(); }
        });


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { goSettings(); }
        });

        btnAssig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { goAssig(); }
        });


    }

    public void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            startActivity(new Intent(MainScreenActivity.this, LoginActivity.class));
        }
    }

    public void goProfile() {
        startActivity(new Intent(MainScreenActivity.this, ProfilePageActivity.class));
    }

    public void logout() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(MainScreenActivity.this, LoginActivity.class));
    }
    // it is not working
    public void goSettings() {
        startActivity(new Intent(MainScreenActivity.this, SettingsActivity.class));
    }

    public void goAssig() {
        startActivity(new Intent(MainScreenActivity.this, ScrollingActivity.class));
    }
}