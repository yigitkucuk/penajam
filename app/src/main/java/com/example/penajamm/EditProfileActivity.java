package com.example.penajamm;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.penajamm.databinding.ActivityEditProfileBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

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
    private  ImageButton btnBack;
    private Uri imageUri;
    private StorageReference reference = FirebaseStorage.getInstance().getReference();
    private ImageButton backbtn, btnAssig, btnMainScreen, btnProfile, btnSettings, btnList ;
    TextInputLayout  username, realname, location, instruments, description;
    Button send, post;
    Button uploadbtn;

    ImageView firebaseimage;
    Userbase dao = new Userbase("User");




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

        username = (TextInputLayout) findViewById(R.id.editusername);
        realname = (TextInputLayout) findViewById(R.id.editrealname);
        backbtn = (ImageButton) findViewById(R.id.backbtn);
        send = (Button) findViewById(R.id.edit_profile);
        instruments = (TextInputLayout) findViewById(R.id.instruments);
        location = (TextInputLayout) findViewById(R.id.location);
        description = (TextInputLayout) findViewById(R.id.description);
        uploadbtn = (Button) findViewById(R.id.fab);
        firebaseimage = (ImageView) findViewById(R.id.profile_icon);
        btnBack = findViewById(R.id.backbtn);


        btnAssig = findViewById(R.id.btn_Assig);
        btnMainScreen = findViewById(R.id.btn_MainScreen);
        btnProfile = findViewById(R.id.btn_Profile);
        btnSettings = findViewById(R.id.btn_Settings);
        btnList = findViewById(R.id.btnList);


        btnProfile.setOnClickListener(view -> goToProfilePage());

        btnAssig.setOnClickListener(view -> goToNewPosts());

        btnProfile.setOnClickListener(view -> goToUsers());

        backbtn.setOnClickListener(view -> goToMainPage());

        btnMainScreen.setOnClickListener(view -> goToMainPage());

        btnSettings.setOnClickListener(view -> goToSettings());


        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;
        toolBarLayout.setTitle(getTitle());

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User");


        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser userr = auth.getCurrentUser();
        ArrayList<User> users = new ArrayList<>();



        btnBack.setOnClickListener(view -> startActivity(new Intent(EditProfileActivity.this, ProfilePageActivity.class)));

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    User user = dataSnapshot.getValue(User.class);
                    users.add(user);
                }

                for(User u: users) {

                    System.out.println(userr.getEmail());
                    if (u.getEmail().equals( userr.getEmail())) {

                        Glide.with(EditProfileActivity.this).load(u.getImageUri()).into(firebaseimage);


                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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


        uploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageUri != null)
                    uploadToFirebase(imageUri);
                else {
                    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("User");
                    Query query = rootRef.orderByKey().limitToLast(20);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {

                                System.out.println(childSnapshot.getKey());
                                System.out.println(location.getEditText().getText().toString().equals(""));
                                String modelId = childSnapshot.getKey();
                                User usertmp = childSnapshot.getValue(User.class);
                                if (usertmp.getEmail().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
                                    if (!(location.getEditText().getText().toString().equals("")))
                                        usertmp.setLocation(location.getEditText().getText().toString());
                                    if (!(instruments.getEditText().getText().toString().equals("")))
                                        usertmp.setInstruments(instruments.getEditText().getText().toString());
                                    if (!(description.getEditText().getText().toString().equals("")))
                                        usertmp.setDescription(description.getEditText().getText().toString());
                                    if (!(username.getEditText().getText().toString().equals("")))
                                        usertmp.setName(username.getEditText().getText().toString());
                                    if (!(realname.getEditText().getText().toString().equals("")))
                                        usertmp.setRealname(realname.getEditText().getText().toString());
                                    dao.update2(modelId, usertmp);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

                startActivity(new Intent(EditProfileActivity.this, ProfilePageActivity.class));
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent , 2);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode ==2 && resultCode == RESULT_OK && data != null){
            imageUri = data.getData();
            firebaseimage.setImageURI(imageUri);
        }
    }

    private void uploadToFirebase(Uri uri){

        final StorageReference fileRef = reference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("User");
                        Query query = rootRef.orderByKey().limitToLast(20);
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {

                                    System.out.println(childSnapshot.getKey());
                                    String modelId = childSnapshot.getKey();
                                    User usertmp =  childSnapshot.getValue(User.class);
                                    if (usertmp.getEmail().equals( FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
                                        if(!(location.getEditText().getText().toString().equals("")))
                                            usertmp.setLocation(location.getEditText().getText().toString());
                                        if(!(instruments.getEditText().getText().toString().equals("")))
                                            usertmp.setInstruments(instruments.getEditText().getText().toString());
                                        if(!(description.getEditText().getText().toString().equals("")))
                                            usertmp.setDescription(description.getEditText().getText().toString());
                                        if (!(username.getEditText().getText().toString().equals("")))
                                            usertmp.setName(username.getEditText().getText().toString());
                                        if (!(realname.getEditText().getText().toString().equals("")))
                                            usertmp.setRealname(realname.getEditText().getText().toString());
                                        usertmp.setImageUri(uri.toString());
                                        dao.update2(modelId, usertmp);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        Toast.makeText(EditProfileActivity.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                        firebaseimage.setImageURI(null);
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(EditProfileActivity.this, "Uploading Failed !!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getFileExtension(Uri mUri){

        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));

    }

    public void goToProfilePage() {
        startActivity(new Intent(EditProfileActivity.this, ProfilePageActivity.class));
    }

    public void goToSettings() {
        startActivity(new Intent(EditProfileActivity.this, SettingsActivity.class));
    }

    public void goToNewPosts() {
        startActivity(new Intent(EditProfileActivity.this, NewPostActivity.class));
    }

    public void goToMainPage() {
        startActivity(new Intent(EditProfileActivity.this, MainScreenActivity.class));
    }

    public void goToUsers() {
        startActivity(new Intent(EditProfileActivity.this, Userlist.class));
    }


}
