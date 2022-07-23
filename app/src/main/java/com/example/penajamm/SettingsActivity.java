package com.example.penajamm;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.auth.FirebaseAuth;

public class SettingsActivity extends AppCompatActivity {

    private ImageButton backbtn, btnAssig, btnMainScreen, btnProfile ;
    private Button btnLogout, btnEditProfile;
    private SwitchMaterial switchMaterial, switchMaterial2, switchMaterial3 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        backbtn = findViewById(R.id.backbtn);
        btnAssig = findViewById(R.id.btn_Assig);
        btnMainScreen = findViewById(R.id.btn_MainScreen);
        btnLogout = findViewById(R.id.btnLogout);
        btnEditProfile = findViewById(R.id.edit_profile);
        switchMaterial = findViewById(R.id.switch_material);
        switchMaterial2 = findViewById(R.id.switch_material2);
        switchMaterial3 = findViewById(R.id.switch_material3);
        btnProfile = findViewById(R.id.btn_Profile);

        btnProfile.setOnClickListener(view -> goProfile());


        btnLogout.setOnClickListener(view -> logout());

        btnAssig.setOnClickListener(view -> goAssig());

        backbtn.setOnClickListener(view -> goMain());

        btnMainScreen.setOnClickListener(view -> goMain());

        btnEditProfile.setOnClickListener(view -> goEditProfile());

        //switchMaterial.isChecked = true;
        switchMaterial.setOnClickListener(view -> switchButton());

        //switchMaterial.isChecked = true;
        switchMaterial2.setOnClickListener(view -> switchButton());

        //switchMaterial.isChecked = true;
        switchMaterial3.setOnClickListener(view -> switchButton());
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
        startActivity(new Intent(SettingsActivity.this, EditProfileActivity.class));
    }

    public void goProfile() {
        startActivity(new Intent(SettingsActivity.this, ProfilePageActivity.class));
    }

    public void switchButton() {
        switchMaterial.setOnCheckedChangeListener((compoundButton, b) -> Toast.makeText(SettingsActivity.this, ":)", Toast.LENGTH_SHORT ).show());
    }



}