package com.example.penajamm;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;



public class ProfileActivity extends AppCompatActivity implements Navigation {
    Button selectImageBtn;
    ImageView imageView;
    private ImageButton backbtn, btnAssig, btnMainScreen, btnProfile, btnList ;

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
        btnList = findViewById(R.id.btnList);

        btnProfile.setOnClickListener(view -> goToProfilePage());

        btnAssig.setOnClickListener(view -> goToNewPosts());

        backbtn.setOnClickListener(view -> goToSettings());

        btnMainScreen.setOnClickListener(view -> goToMainPage());

        btnList.setOnClickListener(view -> goToUsers());

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

    public void goToProfilePage() {
        startActivity(new Intent(ProfileActivity.this, ProfilePageActivity.class));
    }

    public void goToSettings() {
        startActivity(new Intent(ProfileActivity.this, SettingsActivity.class));
    }

    public void goToNewPosts() {
        startActivity(new Intent(ProfileActivity.this, NewPostActivity.class));
    }

    public void goToMainPage() {
        startActivity(new Intent(ProfileActivity.this, MainScreenActivity.class));
    }

    public void goToUsers() {
        startActivity(new Intent(ProfileActivity.this, Userlist.class));
    }

}

