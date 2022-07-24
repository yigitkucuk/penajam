package com.example.penajamm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainScreenActivity extends AppCompatActivity implements Navigation {

    private FirebaseAuth mAuth;
    private Button btnLogout;
    private ImageButton btnList;
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
        btnList = findViewById(R.id.btnList);

        btnProfile.setOnClickListener(view -> goToProfilePage());

        btnList.setOnClickListener(view -> goToUsers());


        btnLogout.setOnClickListener(view -> logout());


        btnSettings.setOnClickListener(view -> goToSettings());

        btnAssig.setOnClickListener(view -> goToNewPosts());


    }

    public void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            startActivity(new Intent(MainScreenActivity.this, LoginActivity.class));
        }
    }

    public void goToProfilePage() {
        startActivity(new Intent(MainScreenActivity.this, ProfilePageActivity.class));
    }

    public void goToSettings() {
        startActivity(new Intent(MainScreenActivity.this, SettingsActivity.class));
    }

    public void goToNewPosts() {
        startActivity(new Intent(MainScreenActivity.this, NewPostActivity.class));
    }

    public void goToMainPage() {
        startActivity(new Intent(MainScreenActivity.this, MainScreenActivity.class));
    }

    public void goToUsers() {
        startActivity(new Intent(MainScreenActivity.this, Userlist.class));
    }

    public void logout() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(MainScreenActivity.this, LoginActivity.class));
    }
}