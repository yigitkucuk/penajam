
package com.example.penajamm;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class ChatActivity extends AppCompatActivity {
    
    private RecyclerViewAdapter adapter;
    private RecyclerView recyclerView;
    private ArrayList<Message> list;

    private DatabaseReference db;
    private TextInputLayout message;
    private FloatingActionButton send;

    private String timeStamp;

    private FirebaseAuth auth;
    private FirebaseUser user;

    protected ArrayList<Message> getList(){
        return this.list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) throws NullPointerException{

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        list = new ArrayList<>();

        send = findViewById(R.id.fab_send);
        message = findViewById(R.id.message);
        recyclerView = findViewById(R.id.recyclerview);

        db = FirebaseDatabase.getInstance().getReference();

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        String uEmail = user.getEmail();
        timeStamp = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

                db.child("Messages").push().setValue(new Message(uEmail, msg, timeStamp)).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        message.getEditText().setText("");
                    }

                });
                }
        });

        adapter = new RecyclerViewAdapter(this, list);
        LinearLayoutManager llm = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
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
