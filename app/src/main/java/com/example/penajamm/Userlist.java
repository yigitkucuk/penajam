package com.example.penajamm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Userlist extends AppCompatActivity implements Navigation {

    private ImageButton btnBack, btnSettings, btnMainScreen, btnProfile, btnAssig;

    RecyclerView recyclerView;
    ArrayList<User> list;
    DatabaseReference databaseReference;
    MyAdapter adapter;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Userlist.this, ShowUserActivity.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userentry);

        btnSettings = findViewById(R.id.btn_Settings);
        btnMainScreen = findViewById(R.id.btn_MainScreen);
        btnProfile = findViewById(R.id.btn_Profile);
        btnAssig = findViewById(R.id.btn_Assig);
        btnBack = findViewById(R.id.backbtn);

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { goToProfilePage(); }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { goToSettings(); }
        });

        btnMainScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { goToMainPage(); }
        });

        btnAssig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { goToNewPosts(); }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToMainPage();
            }
        });

        recyclerView = findViewById(R.id.recycleview);
        databaseReference = FirebaseDatabase.getInstance().getReference("User");
        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter =  new MyAdapter(this,list);
        recyclerView.setAdapter(adapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    User user = dataSnapshot.getValue(User.class);
                    list.add(user);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void goToProfilePage() {
        startActivity(new Intent(Userlist.this, ProfilePageActivity.class));
    }

    public void goToSettings() {
        startActivity(new Intent(Userlist.this, ChatActivity.class));
    }

    public void goToNewPosts() {
        startActivity(new Intent(Userlist.this, NewPostActivity.class));
    }

    public void goToMainPage() {
        startActivity(new Intent(Userlist.this, MainScreenActivity.class));
    }

    public void goToUsers() {
        startActivity(new Intent(Userlist.this, Userlist.class));
    }


}