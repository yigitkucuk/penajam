package com.example.penajamm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PrivateChatroomActivity extends AppCompatActivity {

    private final List<MessagesList> messagesLists = new ArrayList<>();
    private String pusername;
    private String pemail;
    private String pname;
    private RecyclerView pmessagesRecyclerView;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://penajam-b-default-rtdb.firebaseio.com");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_chatroom);

        final CircleImageView puserProfilePic = findViewById(R.id.puserProfilePic);

        pmessagesRecyclerView = findViewById(R.id.pmessagesRecyclerView);

        pusername = getIntent().getStringExtra("pusername");
        pemail = getIntent().getStringExtra("pemail");
        pname = getIntent().getStringExtra("pname");

        pmessagesRecyclerView.setHasFixedSize(true);
        pmessagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        ProgressDialog pprogressDialog = new ProgressDialog(this);
        pprogressDialog.setCancelable(false);
        pprogressDialog.setMessage("Loading...");
        pprogressDialog.show();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                final String pprofilePicUrl = snapshot.child("User").child(pusername).child("pprofile_pic").getValue(String.class);

                if(!pprofilePicUrl.isEmpty()) {

                    Picasso.get().load(pprofilePicUrl).into(puserProfilePic);

                }



                pprogressDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                pprogressDialog.dismiss();

            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                messagesLists.clear();
                for(DataSnapshot dataSnapshot: snapshot.child("User").getChildren()) {

                    final String getUsername = dataSnapshot.getKey();

                    if(!getUsername.equals(pusername)) {
                        final String getName = dataSnapshot.child("realname").getValue(String.class);
                        final String getProfilePic = dataSnapshot.child("imageUri").getValue(String.class);

                        MessagesList pMessagesList = new MessagesList(getName,getUsername, "", getProfilePic,0);
                        messagesLists.add(pMessagesList);
                    }
                }

                pmessagesRecyclerView.setAdapter(new MessagesAdapter(messagesLists, PrivateChatroomActivity.this));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}