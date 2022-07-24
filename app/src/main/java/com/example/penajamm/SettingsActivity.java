package com.example.penajamm;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.auth.FirebaseAuth;

public class SettingsActivity extends AppCompatActivity implements Navigation{

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

        btnProfile.setOnClickListener(view -> goToProfilePage());

        btnLogout.setOnClickListener(view -> logout());

        btnAssig.setOnClickListener(view -> goToNewPosts());

        backbtn.setOnClickListener(view -> goToMainPage());

        btnMainScreen.setOnClickListener(view -> goToMainPage());

        btnEditProfile.setOnClickListener(view -> goToEditProfile());

        //switchMaterial.isChecked = true;
        switchMaterial.setOnClickListener(view -> switchButton());

        //switchMaterial.isChecked = true;
        switchMaterial2.setOnClickListener(view -> switchButton());

        //switchMaterial.isChecked = true;
        switchMaterial3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(switchMaterial3.isSelected()){
                    unmute();
                }else{
                    mute();
                }
            }
        });
    }

    private void mute() {
        //mute audio
        AudioManager amanager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        amanager.setStreamMute(AudioManager.STREAM_NOTIFICATION, true);
    }

    public void unmute() {
        //unmute audio
        AudioManager amanager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        amanager.setStreamMute(AudioManager.STREAM_NOTIFICATION, false);
    }

    public void logout() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(SettingsActivity.this, LoginActivity.class));
    }

    public void goToProfilePage() {
        startActivity(new Intent(SettingsActivity.this, ProfilePageActivity.class));
    }

    public void goToSettings() {
        startActivity(new Intent(SettingsActivity.this, ChatActivity.class));
    }

    public void goToNewPosts() {
        startActivity(new Intent(SettingsActivity.this, NewPostActivity.class));
    }

    public void goToMainPage() {
        startActivity(new Intent(SettingsActivity.this, MainScreenActivity.class));
    }

    public void goToUsers() {
        startActivity(new Intent(SettingsActivity.this, Userlist.class));
    }

    public void goToEditProfile(){
        startActivity(new Intent(SettingsActivity.this, EditProfileActivity.class));
    }

    public void switchButton() {
        switchMaterial.setOnCheckedChangeListener((compoundButton, b) -> Toast.makeText(SettingsActivity.this, ":)", Toast.LENGTH_SHORT ).show());
    }



}