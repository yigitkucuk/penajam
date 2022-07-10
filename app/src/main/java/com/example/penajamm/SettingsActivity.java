package com.example.penajamm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Toast;

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

        //switchMaterial.isChecked = true;
        switchMaterial.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) { switchButton(); }
        });

        //switchMaterial.isChecked = true;
        switchMaterial2.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) { switchButton(); }
        });

        //switchMaterial.isChecked = true;
        switchMaterial3.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) { switchButton(); }
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

    public void goProfile() {
        startActivity(new Intent(SettingsActivity.this, ProfilePageActivity.class));
    }

    public void switchButton() {
        switchMaterial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Toast.makeText(SettingsActivity.this, ":)", Toast.LENGTH_SHORT ).show();
            }
        });
    }



}