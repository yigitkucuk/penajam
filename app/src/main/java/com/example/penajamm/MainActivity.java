package com.example.penajamm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText email, password;
    private Button btnRegister;
    private TextView textLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText edit_name = findViewById(R.id.register_username);
        final EditText edit_position = findViewById(R.id.register_realname);

        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.register_email);
        password = findViewById(R.id.register_password);
        btnRegister = findViewById(R.id.register);
        textLogin = findViewById(R.id.text_login);

        Userbase dao = new Userbase();
        User emp_edit = (User) getIntent().getSerializableExtra("EDIT");
        if (emp_edit != null) {
            btnRegister.setText("UPDATE");
            edit_name.setText(emp_edit.getName());
            edit_position.setText(emp_edit.getPosition());

        } else {
            btnRegister.setText("SUBMIT");

        }
        btnRegister.setOnClickListener(v ->
        {
            register();
            User emp = new User(edit_name.getText().toString(), edit_position.getText().toString());
            if (emp_edit == null) {
                dao.add(emp).addOnSuccessListener(suc ->
                {
                    Toast.makeText(this, "Record is inserted", Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(er ->
                {
                    Toast.makeText(this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
                });
            } else {
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("Username", edit_name.getText().toString());
                hashMap.put("Real Name", edit_position.getText().toString());
                dao.update(emp_edit.getKey(), hashMap).addOnSuccessListener(suc ->
                {
                    Toast.makeText(this, "Record is updated", Toast.LENGTH_SHORT).show();
                    finish();
                }).addOnFailureListener(er ->
                {
                    Toast.makeText(this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
        });

    }

    private void register() {
        System.out.println("HELLO");
        String user = email.getText().toString().trim();
        String pass = password.getText().toString().trim();

        if (user.isEmpty()) {
            email.setError("Email cannot be empty");
        }

        if (pass.isEmpty()) {
            password.setError("Password cannot be empty");
        } else {
            mAuth.createUserWithEmailAndPassword(user, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "User registered succesfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, MainActivity.class));
                    } else {
                        Toast.makeText(MainActivity.this, "Registration failed" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}
