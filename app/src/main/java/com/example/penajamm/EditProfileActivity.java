package com.example.penajamm;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.penajamm.databinding.ActivityEditProfileBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EditProfileActivity extends AppCompatActivity {

    private ActivityEditProfileBinding binding;
    private FirebaseAuth cAuth;
    private TextView cforgotTextLink;
    private Button deleteAccount;
    private ProgressBar progressBar;
    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
    private TextView userEmail;
    private Button changePassword;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        deleteAccount = findViewById(R.id.btnDeleteAccount);
        progressBar = findViewById(R.id.progressBar);
        userEmail = findViewById(R.id.tvUserEmail);
        changePassword = findViewById(R.id.btnChangePassword);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        userEmail.setText(firebaseUser.getEmail());

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;
        toolBarLayout.setTitle(getTitle());

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditProfileActivity.this, ChangePasswordActivity.class));
            }
        });

        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(EditProfileActivity.this);
                dialog.setTitle("Are you sure?");
                dialog.setMessage("Deleting this account will result in completely removing your account from the system and you will not be able to access the app.");
                dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        progressBar.setVisibility(View.VISIBLE);
                        firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                progressBar.setVisibility(View.GONE);

                                if(task.isSuccessful()){
                                    Toast.makeText(EditProfileActivity.this, "Account deleted.", Toast.LENGTH_LONG).show();

                                    Intent intent = new Intent(EditProfileActivity.this, RegisterActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(EditProfileActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }

                            }
                        });

                    }
                });

                dialog.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });

                AlertDialog alertDialog = dialog.create();
                alertDialog.show();


            }
        });

        FloatingActionButton fab = binding.fab;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


    }
    }
