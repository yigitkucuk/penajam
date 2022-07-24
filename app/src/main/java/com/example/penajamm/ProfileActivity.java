package com.example.penajamm;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;



public class ProfileActivity extends AppCompatActivity {
    Button selectImageBtn;
    ImageView imageView;
    private ImageButton backbtn, btnAssig, btnMainScreen, btnProfile ;

    static final int SELECT_IMAGE = 12;
    Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        backbtn = findViewById(R.id.backbtn);
        btnAssig = findViewById(R.id.btn_Assig);
        btnMainScreen = findViewById(R.id.btn_MainScreen);
        btnProfile = findViewById(R.id.btn_Profile);

        btnProfile.setOnClickListener(view -> goProfile());


        btnAssig.setOnClickListener(view -> goAssig());

        backbtn.setOnClickListener(view -> goSett());

        btnMainScreen.setOnClickListener(view -> goMain());

        selectImageBtn = findViewById(R.id.edit_profile);
        imageView = findViewById(R.id.profile_icon);
        selectImageBtn.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, SELECT_IMAGE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_IMAGE && resultCode == RESULT_OK) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);

        }
        else if (resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "picture selection canceled", Toast.LENGTH_SHORT ).show();
        }
    }

    public void goMain() {
        startActivity(new Intent(ProfileActivity.this, MainScreenActivity.class));
    }
    public void goAssig() {
        startActivity(new Intent(ProfileActivity.this, ScrollingActivity.class));
    }

    public void goProfile() {
        startActivity(new Intent(ProfileActivity.this, ProfilePageActivity.class));
    }

    public void goSett() {
        startActivity(new Intent(ProfileActivity.this, SettingsActivity.class));
    }

}