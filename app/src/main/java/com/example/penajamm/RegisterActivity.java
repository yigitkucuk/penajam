package com.example.penajamm;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.MemoryFile;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
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

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    ////////////////////////7
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://penajam-b-default-rtdb.firebaseio.com");
    /////////////////////////

    private FirebaseAuth mAuth;
    private EditText email, password;
    private Button btnRegister, btnLogin;
    private TextView textLogin;

    private AlertDialog.Builder dialogbuilder;
    private AlertDialog dialog;

    private StorageReference reference = FirebaseStorage.getInstance().getReference();
    DatabaseReference db;
    TextInputLayout  location, instruments, description;
    Button send, post;
    Button uploadbtn;
    ImageButton backbtn;
    ImageView firebaseimage;
    Uri imageUri;
    Userbase dao = new Userbase("User");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final EditText edit_username = findViewById(R.id.register_username);
        final EditText edit_realname = findViewById(R.id.register_realname);

        /////////////////////////////////////
        final EditText username = findViewById(R.id.register_username);
        final EditText name = findViewById(R.id.register_realname);
        final EditText emailll = findViewById(R.id.editusername);
        //////////////////////////////////////


        btnLogin = findViewById(R.id.text_login);
        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.editusername);
        password = findViewById(R.id.register_password);
        btnRegister = findViewById(R.id.register);
        textLogin = findViewById(R.id.text_login);
        db = FirebaseDatabase.getInstance().getReference();


        //////////////////////////////////////7
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");

        if(!MemoryData.getData(this).isEmpty()){
            Intent intent = new Intent(RegisterActivity.this,PrivateChatroomActivity.class);
            intent.putExtra("name",MemoryData.getData(this));
            intent.putExtra("realname", MemoryData.getName(this));
            intent.putExtra("email","");
            startActivity(intent);
            finish();

        }
        ///////////////////////////////////////7



        //////////////////////////////////////
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                progressDialog.show();


                final String nameTxt = name.getText().toString();
                final String usernameTxt = username.getText().toString();
                final String emailTxt = emailll.getText().toString();

                if(nameTxt.isEmpty() || usernameTxt.isEmpty() || emailTxt.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "All fields are required.", Toast.LENGTH_SHORT).show();

                    progressDialog.dismiss();

                }

                else{
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {


                            progressDialog.dismiss();


                            if(snapshot.child("User").hasChild(usernameTxt)) {

                                Toast.makeText(RegisterActivity.this, "Already exists", Toast.LENGTH_SHORT).show();
                            }

                            else {
                                databaseReference.child("User").child(usernameTxt).child("email").setValue(emailTxt);
                                databaseReference.child("User").child(usernameTxt).child("realname").setValue(nameTxt);
                                databaseReference.child("user").child(usernameTxt).child("profile_pic").setValue("");

                                MemoryData.saveData(usernameTxt,RegisterActivity.this);

                                MemoryData.saveName(nameTxt,RegisterActivity.this);
                                Toast.makeText(RegisterActivity.this, "Sucess", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(RegisterActivity.this,PrivateChatroomActivity.class);
                                intent.putExtra("name",usernameTxt);
                                intent.putExtra("realname",nameTxt);
                                intent.putExtra("email",emailTxt);
                                startActivity(intent);
                                finish();

                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {


                            progressDialog.dismiss();


                        }
                    });

                }
            }
        });
        //////////////////////////////////////////////////7



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            Intent i = new Intent(RegisterActivity.this, MainScreenActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }


        btnRegister.setOnClickListener(v ->
        {
            register(edit_username,edit_realname);


        });

    }

    private void register(EditText edit_username, EditText edit_realname) {
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
                        Toast.makeText(RegisterActivity.this, "User registered succesfully", Toast.LENGTH_SHORT).show();
                        createNewPostDiaglog(edit_username,edit_realname);
                    } else {
                        Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }


        User emp_edit = (User) getIntent().getSerializableExtra("EDIT");
        if (emp_edit != null) {
            btnRegister.setText("UPDATE");
            edit_username.setText(emp_edit.getName());
            edit_realname.setText(emp_edit.getRealname());

        } else {
            btnRegister.setText("SUBMIT");

        }
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                //pst = new Post(uName, ttl, loc, msg);
                User emp = new User(email.getText().toString(), edit_username.getText().toString(), edit_realname.getText().toString());


                if (emp_edit == null) {
                    dao.add(emp).addOnSuccessListener(suc ->
                    {
                        Toast.makeText(RegisterActivity.this, "Record is inserted", Toast.LENGTH_SHORT).show();
                    }).addOnFailureListener(er ->
                    {
                        Toast.makeText(RegisterActivity.this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
                    });
                } else {
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("Username", edit_username.getText().toString());
                    hashMap.put("Real Name", edit_realname.getText().toString());
                    hashMap.put("Email", email.getText().toString());

                    dao.update(emp_edit.getKey(), hashMap).addOnSuccessListener(suc ->
                    {
                        Toast.makeText(RegisterActivity.this, "Record is updated", Toast.LENGTH_SHORT).show();
                        finish();

                    }).addOnFailureListener(er ->
                    {
                        Toast.makeText(RegisterActivity.this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
                    });


                };





            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void createNewPostDiaglog(EditText edit_username, EditText edit_realname) {
        dialogbuilder = new AlertDialog.Builder(this);
        final View contactPopupView = getLayoutInflater().inflate(R.layout.activity_profile, null);
        backbtn = (ImageButton) contactPopupView.findViewById(R.id.backbtn);
        send = (Button) contactPopupView.findViewById(R.id.edit_profile);
        instruments = (TextInputLayout) contactPopupView.findViewById(R.id.instruments);
        location = (TextInputLayout) contactPopupView.findViewById(R.id.location);
        description = (TextInputLayout) contactPopupView.findViewById(R.id.description);
        uploadbtn = (Button) contactPopupView.findViewById(R.id.applyBtn);
        firebaseimage = (ImageView) contactPopupView.findViewById(R.id.firebaseimage);




        dialogbuilder.setView(contactPopupView);
        dialog = dialogbuilder.create();
        dialog.show();

        String ins = instruments.getEditText().getText().toString();
        String loc = location.getEditText().getText().toString();
        String msg = description.getEditText().getText().toString();

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        uploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if (imageUri != null){
                    uploadToFirebase(imageUri);
                }
                else{
                    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("User");
                    Query query = rootRef.orderByKey().limitToLast(1);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {

                                System.out.println(childSnapshot.getKey());
                                String modelId = childSnapshot.getKey();
                                User usertmp =  childSnapshot.getValue(User.class);
                                usertmp.setLocation(location.getEditText().getText().toString());
                                usertmp.setInstruments(instruments.getEditText().getText().toString());
                                usertmp.setDescription(description.getEditText().getText().toString());
                                usertmp.setImageUri("https://firebasestorage.googleapis.com/v0/b/penajam-b.appspot.com/o/Screen%20Shot%202022-07-23%20at%2021.23.41.png?alt=media&token=506fe97e-b024-46f4-914d-360e4f3cf389");
                                dao.update2( modelId, usertmp );
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    //ProfileModel model = new ProfileModel("https://firebasestorage.googleapis.com/v0/b/penajam-b.appspot.com/o/Screen%20Shot%202022-07-23%20at%2021.23.41.png?alt=media&token=506fe97e-b024-46f4-914d-360e4f3cf389");
                    //HashMap<String, Object> hashMap2 = new HashMap<>();
                    //hashMap2.put("ProfileImage", model.getImageUri().toString());
                    //String modelId = query.getRef().toString();
                    //dao.update(modelId, hashMap2 );

                }




                //register();


                dialog.dismiss();
                startActivity(new Intent(RegisterActivity.this, VerificationActivity.class));

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
                        Query query = rootRef.orderByKey().limitToLast(1);
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {

                                    System.out.println(childSnapshot.getKey());
                                    String modelId = childSnapshot.getKey();
                                    User usertmp =  childSnapshot.getValue(User.class);
                                    usertmp.setLocation(location.getEditText().getText().toString());
                                    usertmp.setInstruments(instruments.getEditText().getText().toString());
                                    usertmp.setDescription(description.getEditText().getText().toString());
                                    usertmp.setImageUri(uri.toString());
                                    dao.update2( modelId, usertmp );
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        Toast.makeText(RegisterActivity.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
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

                Toast.makeText(RegisterActivity.this, "Uploading Failed !!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getFileExtension(Uri mUri){

        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));

    }
}
