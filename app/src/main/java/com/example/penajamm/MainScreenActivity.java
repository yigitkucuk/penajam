package com.example.penajamm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainScreenActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button btnLogout;
    private Button btnList;
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

        btnProfile.setOnClickListener(view -> goProfile());

        btnList.setOnClickListener(view -> goList());


        btnLogout.setOnClickListener(view -> logout());


        btnSettings.setOnClickListener(view -> goSettings());

        btnAssig.setOnClickListener(view -> goAssig());


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
        startActivity(new Intent(MainScreenActivity.this, ChatActivity.class));
    }

    public void goAssig() {
        startActivity(new Intent(MainScreenActivity.this, NewPostActivity.class));
    }

    public void goMain() {
        startActivity(new Intent(MainScreenActivity.this, MainScreenActivity.class));
    }

    public void goList() {
        startActivity(new Intent(MainScreenActivity.this, Userlist.class));
    }
}