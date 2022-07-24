package com.example.penajamm;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

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

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User");
        DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("ProfileImage");

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser userr = auth.getCurrentUser();
        ArrayList<User> users = new ArrayList<>();

        ImageView profileicon = findViewById(R.id.profile_icon);
        TextView textView = findViewById(R.id.name);
        TextView usernameView = findViewById(R.id.username);


        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    User user = dataSnapshot.getValue(User.class);
                    users.add(user);
                }

                for(User u: users) {


                    if (u.getEmail().equals( userr.getEmail())) {

                        Glide.with(SettingsActivity.this).load(u.getImageUri()).into(profileicon);
                        textView.setText(u.getRealname());
                        usernameView.setText(u.getName());
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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