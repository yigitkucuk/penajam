package com.example.penajamm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ShowUserActivity extends AppCompatActivity {

    Button btnView;
    Button btnInsert;
    EditText name;
    EditText realname;
    EditText email;
    DatabaseReference databaseUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);   //maybe activity_register activity_main_screen activity_show_user

        btnInsert = findViewById(R.id.register);

        name = findViewById(R.id.register_username);
        realname = findViewById(R.id.register_realname);
        email = findViewById(R.id.editusername);
        databaseUsers = FirebaseDatabase.getInstance().getReference();

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertData();
            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShowUserActivity.this, Userlist.class));
                finish();
            }
        });
    }

    private void InsertData() {

        String username = name.getText().toString();
        String userrealname = realname.getText().toString();
        String useremail = email.getText().toString();
        String id = databaseUsers.push().getKey();

        User user = new User(useremail, username,userrealname);
        databaseUsers.child("User").child(id).setValue(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(ShowUserActivity.this,"Inserted",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}