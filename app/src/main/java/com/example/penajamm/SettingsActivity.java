package com.example.penajamm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;

public class SettingsActivity extends AppCompatActivity {

    private ImageButton backbtn, btnAssig, btnMainScreen ;
    private Button btnLogout, btnEditProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        backbtn = findViewById(R.id.backbtn);
        btnAssig = findViewById(R.id.btn_Assig);
        btnMainScreen = findViewById(R.id.btn_MainScreen);
        btnLogout = findViewById(R.id.btnLogout);
        btnEditProfile = findViewById(R.id.edit_profile);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

        btnAssig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { goAssig(); }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goMain();
            }
        });

        btnMainScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goMain();
            }
        });

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goEditProfile();
            }
        });
    }

    public void logout() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(SettingsActivity.this, LoginActivity.class));
    }

    public void goMain() {
        startActivity(new Intent(SettingsActivity.this, MainScreenActivity.class));
    }
    public void goAssig() {
        startActivity(new Intent(SettingsActivity.this, ScrollingActivity.class));
    }

    public void goEditProfile() {
        startActivity(new Intent(SettingsActivity.this, ProfileActivity.class));
    }



}