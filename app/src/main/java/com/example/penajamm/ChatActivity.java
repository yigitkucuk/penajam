package com.example.penajamm;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ChatActivity extends AppCompatActivity {
    RecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<Message> list;

    DatabaseReference db;
    TextInputLayout message;
    FloatingActionButton send;

    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        send = findViewById(R.id.fab_send);
        message = findViewById(R.id.message);
        recyclerView = findViewById(R.id.recyclerview);
        list = new ArrayList<>();

        db = FirebaseDatabase.getInstance().getReference();

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        String uId = user.getUid();
        String uEmail = user.getEmail();
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = message.getEditText().getText().toString();

                db.child("Messages").push().setValue(new Message(uEmail, msg, timeStamp)).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        message.getEditText().setText("");

                    }
                });
            }
        });

        adapter = new RecyclerViewAdapter(this, list);
        LinearLayoutManager llm = new LinearLayoutManager(this, RecyclerView.VERTICAL, true);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);

    }

    protected void onStart(){
        super.onStart();
        receiveMessages();
    }

    private void receiveMessages(){
        db.child("Messages").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Message message = snap.getValue(Message.class);
                    list.add(message);
                    adapter.notifyDataSetChanged();


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}