package com.example.penajamm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.provider.ContactsContract;

import com.example.penajamm.messages.MessagesAdapter;
import com.example.penajamm.messages.MessagesList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PrivateChatroomActivity extends AppCompatActivity {

    private final List<MessagesList> messagesLists = new ArrayList<>();
    private String pusername;
    private String pemail;
    private String pname;
    private int punseenMessages = 0;
    private String plastMessage = "";
    private String pchatKey = "";
    private boolean dataSet = false;
    private RecyclerView pmessagesRecyclerView;
    private MessagesAdapter pmessagesAdapter;
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
        pmessagesAdapter = new MessagesAdapter(messagesLists, PrivateChatroomActivity.this));
        pmessagesRecyclerView.setAdapter(pmessagesAdapter);


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
                punseenMessages = 0;
                plastMessage ="";
                pchatKey ="";
                for(DataSnapshot dataSnapshot: snapshot.child("User").getChildren()) {

                    final String getUsername = dataSnapshot.getKey();

                    dataSet = false;
                    if(!getUsername.equals(pusername)) {
                        final String getName = dataSnapshot.child("realname").getValue(String.class);
                        final String getProfilePic = dataSnapshot.child("imageUri").getValue(String.class);



                        databaseReference.child("pchat").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                int pgetChatCounts = (int) snapshot.getChildrenCount();

                                if(pgetChatCounts > 0) {

                                    for(DataSnapshot dataSnapshot1: snapshot.getChildren()) {

                                        final String pgetKey = dataSnapshot1.getKey();
                                        pchatKey = pgetKey;
                                        if(dataSnapshot1.hasChild("puser_1") && dataSnapshot1.hasChild("puser_2") && dataSnapshot1.hasChild("messages")){
                                            final String pgetUserOne = dataSnapshot1.child("puser_1").getValue(String.class);
                                            final String pgetUserTwo = dataSnapshot1.child("puser_2").getValue(String.class);

                                            if((pgetUserOne.equals(getUsername) && pgetUserTwo.equals(pusername)) || (pgetUserOne.equals(pusername) && pgetUserTwo.equals(getUsername))) {

                                                for (DataSnapshot chatDataSnapshot: dataSnapshot1.child("messages").getChildren()) {

                                                    final long pgetMessageKey = Long.parseLong(chatDataSnapshot.getKey());
                                                    final long pgetLastSeenMessage = MemoryData.getLastMsgTS(PrivateChatroomActivity.this,pgetKey);
                                                    plastMessage = chatDataSnapshot.child("msg").getValue(String.class);
                                                    if(pgetMessageKey > pgetLastSeenMessage){
                                                        punseenMessages++;
                                                    }
                                                }
                                            }
                                        }

                                    }
                                }
                                if(!dataSet){
                                    dataSet = true;
                                    MessagesList pMessagesList = new MessagesList(getName,getUsername, plastMessage, getProfilePic,punseenMessages, pchatKey);
                                    messagesLists.add(pMessagesList);
                                    pmessagesAdapter.updateData(messagesLists);
                                }



                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}