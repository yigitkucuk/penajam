package com.example.penajamm.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.penajamm.MemoryData;
import com.example.penajamm.R;
import com.google.firebase.Timestamp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class Chat extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://penajam-b-default-rtdb.firebaseio.com");
    private final List<ChatList> chatLists = new ArrayList<>();
    private String pchatKey;
    String pgetUsername2 = "";
    private RecyclerView pchattingRecyclerView;
    private ChatAdapter chatAdapter;
    private boolean loadingFirstTime = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat2);

        final ImageView pbackBtn = findViewById(R.id.pbackBtn);
        final TextView pnameTV = findViewById(R.id.pname);
        final EditText pmessageEditText = findViewById(R.id.pmessageEditTxt);
        final CircleImageView pprofilePic = findViewById(R.id.pprofilePic);
        final ImageView psendBtn = findViewById(R.id.psendBtn);

        pchattingRecyclerView = findViewById(R.id.pchattingRecyclerView);

        final String pgetName = getIntent().getStringExtra("pname");
        final String pgetProfilePic = getIntent().getStringExtra("pprofile_pic");
        pchatKey = getIntent().getStringExtra("pchat_key");
        final String pgetUsername = getIntent().getStringExtra("pusername");

        pgetUsername2 = MemoryData.getData(Chat.this);

        pnameTV.setText(pgetName);
        Picasso.get().load(pgetProfilePic).into(pprofilePic);
        pchattingRecyclerView.setHasFixedSize(true);
        pchattingRecyclerView.setLayoutManager(new LinearLayoutManager(Chat.this));
        chatAdapter = new ChatAdapter(chatLists,Chat.this);
        pchattingRecyclerView.setAdapter(chatAdapter);


        databaseReference.addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        if (pchatKey.isEmpty()) {
                                                            pchatKey = "1";
                                                            if (snapshot.hasChild("chat")) {
                                                                pchatKey = String.valueOf(snapshot.child("chat").getChildrenCount() + 1);
                                                            }
                                                        }
                                                        if (snapshot.hasChild("chat")) {
                                                            chatLists.clear();
                                                            if (snapshot.child("chat").child(pchatKey).hasChild("messages")) {
                                                                for (DataSnapshot messagesSnapshot : snapshot.child("chat").child(pchatKey).child("messages").getChildren()) {
                                                                    if (messagesSnapshot.hasChild("msg") && messagesSnapshot.hasChild("pusername")) {
                                                                        final String pmessageTimestamps = messagesSnapshot.getKey();
                                                                        final String getUsername = messagesSnapshot.child("pusername").getValue(String.class);
                                                                        final String getMsg = messagesSnapshot.child("msg").getValue(String.class);
                                                                        Timestamp timestamp = new Timestamp(Long.parseLong(pmessageTimestamps));
                                                                        Date date = new Date(timestamp.getTime());
                                                                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy ", Locale.getDefault());
                                                                        SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("hh:mm aa", Locale.getDefault());
                                                                        ChatList chatList = new ChatList(getUsername,pgetName, getMsg, simpleDateFormat.format(date),simpleTimeFormat.format(date));
                                                                        chatLists.add(chatList);
                                                                        if (loadingFirstTime||Long.parseLong(pmessageTimestamps) > Long.parseLong(MemoryData.getLastMsgTS(Chat.this, pchatKey))) {
                                                                            loadingFirstTime = false;
                                                                            MemoryData.saveLastMsgTS(pmessageTimestamps, pchatKey, Chat.this);
                                                                            chatAdapter.updateChatList(chatLists);
                                                                            pchattingRecyclerView.scrollToPosition(chatLists.size() - 1);
                                                                        }
                                                                    }

                                                                }
                                                            }

                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {
                                                    }
                                                });

        psendBtn.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View v){
        final String pgetTxtMessage = pmessageEditText.getText().toString();
            final String currentTimestamp = String.valueOf(System.currentTimeMillis()).substring(0, 10);

        databaseReference.child("chat").child(pchatKey).child("puser_1").setValue(pgetUsername2);
        databaseReference.child("chat").child(pchatKey).child("puser_2").setValue(pgetUsername);
        databaseReference.child("chat").child(pchatKey).child("messages").child(currentTimestamp).child("msg").setValue(pgetTxtMessage);
        databaseReference.child("chat").child(pchatKey).child("messages").child(currentTimestamp).child("pusername").setValue(pgetUsername2);

        pmessageEditText.setText("");
    }
    });

        pbackBtn.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View v){
        finish();
    }
    });
}
}