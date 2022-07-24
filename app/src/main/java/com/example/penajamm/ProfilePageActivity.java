package com.example.penajamm;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.penajamm.databinding.ActivityProfilePageBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProfilePageActivity extends AppCompatActivity implements Navigation{

    private ActivityProfilePageBinding binding;
    private ImageButton btnSettings, btnMainScreen, btnAssig, btnBack, btnList;
    private VideoView videoView;
    private TextView textView, usernameView, locationView, pointView, descriptionView, instrumentsView;
    private ArrayList<User> list;
    private ShapeableImageView profileicon;


    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference usersRef = rootRef.child("User");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityProfilePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);

        Userbase userbase = new Userbase();

        list = userbase.getUsers();
        for (User u: list) {
            System.out.println(u.getName());
        }

        FloatingActionButton fab = binding.fab;
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

        btnList.findViewById(R.id.btnList);
        btnSettings = findViewById(R.id.btn_Settings);
        btnMainScreen = findViewById(R.id.btn_MainScreen);
        btnAssig = findViewById(R.id.btn_Assig);
        btnBack = findViewById(R.id.backbtn);
        videoView = findViewById(R.id.videoView);
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.video;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);

        textView = (TextView) findViewById(R.id.name);
        usernameView = (TextView) findViewById(R.id.username);
        instrumentsView = (TextView) findViewById(R.id.instrumentsView);
        locationView = (TextView) findViewById(R.id.name2);
        pointView = (TextView) findViewById(R.id.pointView);
        descriptionView = (TextView) findViewById(R.id.description);
        profileicon = (ShapeableImageView) findViewById(R.id.profile_icon);


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User");
        DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("ProfileImage");

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser userr = auth.getCurrentUser();
        ArrayList<User> users = new ArrayList<>();
        ArrayList<ProfileModel> profileimages = new ArrayList<>();
        

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    User user = dataSnapshot.getValue(User.class);
                    users.add(user);
                }

                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    ProfileModel model = dataSnapshot.getValue(ProfileModel.class);
                    profileimages.add(model);
                }

                for(User u: users) {

                    System.out.println(userr.getEmail());
                    if (u.getEmail().equals( userr.getEmail())) {
                        System.out.println(profileimages.get(0).getImageUri());
                        if(profileimages.get(0).getImageUri() != null)
                            Glide.with(ProfilePageActivity.this).load(profileimages.get(0).getImageUri()).into(profileicon);
                        textView.setText(u.getRealname());
                        usernameView.setText(u.getName());
                        locationView.setText(u.getLocation());
                        instrumentsView.setText("Instruments: " + u.getInstruments());
                        pointView.setText("Point: " + u.getPoint());
                        descriptionView.setText("Description: " + u.getDescription());
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        reference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    System.out.println("c");
                    ProfileModel model = dataSnapshot.getValue(ProfileModel.class);
                    profileimages.add(model);
                }
                System.out.println(profileimages.get(0).getImageUri());
                if(profileimages.get(0).getImageUri() != null)
                    Glide.with(ProfilePageActivity.this).load(profileimages.get(0).getImageUri()).into(profileicon);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);

        btnBack.setOnClickListener(view -> goToMainPage());

        btnSettings.setOnClickListener(view -> goToSettings());

        btnMainScreen.setOnClickListener(view -> goToMainPage());

        btnAssig.setOnClickListener(view -> goToNewPosts());

        btnList.setOnClickListener(view -> goToUsers());
    }

    public void goToProfilePage() {
        startActivity(new Intent(ProfilePageActivity.this, ProfilePageActivity.class));
    }

    public void goToSettings() {
        startActivity(new Intent(ProfilePageActivity.this, SettingsActivity.class));
    }

    public void goToNewPosts() {
        startActivity(new Intent(ProfilePageActivity.this, NewPostActivity.class));
    }

    public void goToMainPage() {
        startActivity(new Intent(ProfilePageActivity.this, MainScreenActivity.class));
    }

    public void goToUsers() {
        startActivity(new Intent(ProfilePageActivity.this, Userlist.class));
    }

}