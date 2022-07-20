package com.example.penajamm;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class NewPostActivity extends AppCompatActivity {
    NewRecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<Post> list;

    DatabaseReference db;
    TextInputLayout title, location, description;
    FloatingActionButton send, post;
    Drawable photo;
    ImageButton backbtn;
    String timeStamp;

    FirebaseAuth auth;
    FirebaseUser user;

    private AlertDialog.Builder dialogbuilder;
    private AlertDialog dialog;



    protected ArrayList<Post> getList(){
        return this.list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        setContentView(R.layout.activity_new_post);

        list = new ArrayList<>();
        post = findViewById(R.id.fab_send);




        recyclerView = findViewById(R.id.recyclerview);

        db = FirebaseDatabase.getInstance().getReference();

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        Userbase dao = new Userbase();


        String uId = user.getUid();
        String uEmail = user.getEmail();
        timeStamp = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewPostDiaglog();
            }
        });



        adapter = new NewRecyclerViewAdapter(this, list);
        LinearLayoutManager llm = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);
    }

    protected void onStart(){
        super.onStart();
        receiveMessages();
    }

    private void receiveMessages(){

        db.child("Posts").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list.clear();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Post post = snap.getValue(Post.class);
                    adapter.addPost(post);
                }
            }

            //TODO
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void createNewPostDiaglog() {
        dialogbuilder = new AlertDialog.Builder(this);
        final View contactPopupView = getLayoutInflater().inflate(R.layout.activity_post, null);
        backbtn = (ImageButton) contactPopupView.findViewById(R.id.backbtn);
        send = (FloatingActionButton) contactPopupView.findViewById(R.id.sendbtn);
        title = (TextInputLayout) contactPopupView.findViewById(R.id.title);
        location = (TextInputLayout) contactPopupView.findViewById(R.id.location);
        description = (TextInputLayout) contactPopupView.findViewById(R.id.description);


        dialogbuilder.setView(contactPopupView);
        dialog = dialogbuilder.create();
        dialog.show();

        String uEmail = user.getEmail();
        timeStamp = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CountDownTimer newtimer = new CountDownTimer(1000000000, 1000) {

                    public void onTick(long millisUntilFinished) {
                        Calendar c = new GregorianCalendar();
                        Date dt = new Date();
                        TimeZone tr = TimeZone.getTimeZone("Asia/Istanbul");
                        c.setTimeZone(tr);
                        timeStamp = new SimpleDateFormat("HH:mm:ss").format(c.getTime());
                    }
                    public void onFinish() {

                    }
                };
                newtimer.start();
                String ttl = title.getEditText().getText().toString();
                String loc = location.getEditText().getText().toString();
                String msg = description.getEditText().getText().toString();
                //Drawable photo = photo.get;

                db.child("Posts").push().setValue(new Post(uEmail, ttl, loc, msg, timeStamp)).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        title.getEditText().setText("");
                        location.getEditText().setText("");
                        if (description != null)
                            description.getEditText().setText("");
                    }

                });

                dialog.dismiss();
            }
        });
    }

    /*public void goPost() {
        startActivity(new Intent(NewPostActivity.this, PostActivity.class));
    }*/
}