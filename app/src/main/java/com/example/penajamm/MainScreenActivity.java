package com.example.penajamm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.penajamm.news.NewsActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainScreenActivity extends AppCompatActivity implements Navigation {

    private FirebaseAuth mAuth;
    private Button btnLogout;
    private ImageButton btnList;
    private ImageButton btnAssig, btnSettings, btnMainScreen, btnProfile, btnChat;
    RecyclerView recyclerView;
    ArrayList<Chat> list;
    DatabaseReference databaseReference;
    MainRecyclerViewAdapter adapter;

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
        btnChat = findViewById(R.id.btn_News);

        btnProfile.setOnClickListener(view -> goToProfilePage());

        btnList.setOnClickListener(view -> goToUsers());

        btnLogout.setOnClickListener(view -> logout());

        btnMainScreen.setOnClickListener(view -> goToMainPage());

        btnSettings.setOnClickListener(view -> goToSettings());

        btnChat.setOnClickListener(view -> goToChat());

        btnAssig.setOnClickListener(view -> goToNewPosts());

        recyclerView = findViewById(R.id.recyclerview);
        databaseReference = FirebaseDatabase.getInstance().getReference("Chat");
        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter =  new MainRecyclerViewAdapter(this,list);
        recyclerView.setAdapter(adapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    Chat chat = dataSnapshot.getValue(Chat.class);
                    list.add(chat);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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

    public void goToChat() {
        startActivity(new Intent(MainScreenActivity.this, NewsActivity.class));
    }

    public void goToUsers() {
        startActivity(new Intent(MainScreenActivity.this, Userlist.class));
    }

    public void logout() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(MainScreenActivity.this, LoginActivity.class));
    }
}