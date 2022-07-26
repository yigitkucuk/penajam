
package com.example.penajamm;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class ChatActivity extends AppCompatActivity implements Navigation {

    private RecyclerViewAdapter adapter;
    private RecyclerView recyclerView;
    private ArrayList<Message> list;
    private ImageButton backBtn;
    private TextView title;
    private ImageView profileImage;

    private DatabaseReference db;
    private TextInputLayout message;
    private FloatingActionButton send;

    private String timeStamp;

    private FirebaseAuth auth;
    private FirebaseUser user;


    protected ArrayList<Message> getList() {
        return this.list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) throws NullPointerException {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        list = new ArrayList<>();
        backBtn = findViewById(R.id.backbtn);
        send = findViewById(R.id.fab_send);
        message = findViewById(R.id.message);
        recyclerView = findViewById(R.id.recyclerview);

        title = findViewById(R.id.titleName);
        profileImage = findViewById(R.id.profile_icon);

        db = FirebaseDatabase.getInstance().getReference();

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        String uEmail = user.getEmail();
        timeStamp = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToMainPage();
            }
        });

        send.setOnClickListener(view -> {

            CountDownTimer newtimer = new CountDownTimer(1000000000, 100) {

                @SuppressLint("SimpleDateFormat")
                public void onTick(long millisUntilFinished) {
                    Calendar c = new GregorianCalendar();
                    TimeZone tr = TimeZone.getTimeZone("Asia/Istanbul");
                    c.setTimeZone(tr);
                    timeStamp = new SimpleDateFormat("HH:mm:ss").format(c.getTime());
                }

                public void onFinish() {

                }
            };
            newtimer.start();

            String msg = message.getEditText().getText().toString();

            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("Chat");
            Query query = rootRef.orderByKey().limitToLast(2);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {

                        Chat chattmp = childSnapshot.getValue(Chat.class);
                        db.child("Chatrooms").child(chattmp.getChatRoomName()).child("Messages").push().setValue(new Message(uEmail, msg, timeStamp)).addOnCompleteListener(task -> message.getEditText().setText(""));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        });

        adapter = new RecyclerViewAdapter(this, list);
        LinearLayoutManager llm = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);
    }

    protected void onStart() {
        super.onStart();
        receiveMessages();
    }

    private void receiveMessages() {

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("Chat");
        Query query = rootRef.orderByKey().limitToLast(2);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    Chat chattmp = childSnapshot.getValue(Chat.class);

                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Chat");
                    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("User");
                    Query query = rootRef.orderByKey().limitToLast(20);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {

                                System.out.println(childSnapshot.getKey());
                                String modelId = childSnapshot.getKey();
                                User usertmp = childSnapshot.getValue(User.class);
                                if (usertmp.getEmail().equals(user.getEmail()) ) {


                                   if(usertmp.getRealname().equals(chattmp.getUserOneName())) {
                                       Glide.with(ChatActivity.this).load(chattmp.getUserTwoPPUri()).into(profileImage);
                                       title.setText(chattmp.getUserTwoName());
                                   }

                                    if(usertmp.getRealname().equals(chattmp.getUserTwoName())) {
                                        Glide.with(ChatActivity.this).load(chattmp.getUserOnePPUri()).into(profileImage);
                                        title.setText(chattmp.getUserOneName());
                                    }



                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    db.child("Chatrooms").child(chattmp.getChatRoomName()).child("Messages").addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            list.clear();
                            for (DataSnapshot snap : snapshot.getChildren()) {
                                Message message = snap.getValue(Message.class);
                                adapter.addMessage(message);
                            }
                        }

                        //TODO
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void goToProfilePage() {
        startActivity(new Intent(ChatActivity.this, ProfilePageActivity.class));
    }

    public void goToSettings() {
        startActivity(new Intent(ChatActivity.this, SettingsActivity.class));
    }

    public void goToNewPosts() {
        startActivity(new Intent(ChatActivity.this, NewPostActivity.class));
    }

    public void goToMainPage() {
        startActivity(new Intent(ChatActivity.this, MainScreenActivity.class));
    }

    @Override
    public void goToChat() {
        startActivity(new Intent(ChatActivity.this, ChatActivity.class));
    }

    public void goToUsers() {
        startActivity(new Intent(ChatActivity.this, Userlist.class));
    }
}