package com.example.penajamm;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.penajamm.databinding.ActivityNewPostBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class NewPostActivity extends AppCompatActivity implements Navigation {
    private ImageButton btnBack, btnSettings, btnMainScreen, btnProfile, btnAssig, btnList;

    NewRecyclerViewAdapter adapter;
    imageRecyclerView adapt;
    RecyclerView recyclerView;
    ArrayList<Post> list;
    ArrayList<Model> mList;

    DatabaseReference db;

    TextInputLayout title, location, description;

    FloatingActionButton send, post;

    ImageButton backbtn;
    String timeStamp;
    Button uploadbtn;
    ImageView firebaseimage;
    private ProgressBar progressBar;
    boolean isSelected = false;
    String pstUri;
    FirebaseAuth auth;
    FirebaseUser user;
    private Post pst;

    ////////////
    ActivityNewPostBinding binding;

    StorageReference storageReference;
    ProgressDialog progressDialog;
    ///////////

    private AlertDialog.Builder dialogbuilder;
    private AlertDialog dialog;

    //vars
    private DatabaseReference root;
    private StorageReference reference = FirebaseStorage.getInstance().getReference();
    private Uri imageUri;


    protected ArrayList<Model> getMList(){
        return this.mList;
    }

    protected ArrayList<Post> getList(){
        return this.list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        btnBack = findViewById(R.id.backbtn);

        btnAssig = findViewById(R.id.btn_Assig);
        btnSettings = findViewById(R.id.btn_Settings);
        btnMainScreen = findViewById(R.id.btn_MainScreen);
        btnProfile = findViewById(R.id.btn_Profile);
        btnList = findViewById(R.id.btnList);

        btnProfile.setOnClickListener(view -> goToProfilePage());

        btnList.setOnClickListener(view -> goToUsers());


        btnMainScreen.setOnClickListener(view -> goToMainPage());

        btnSettings.setOnClickListener(view -> goToSettings());

        btnAssig.setOnClickListener(view -> goToNewPosts());

        list = new ArrayList<>();
        mList = new ArrayList<>();
        post = findViewById(R.id.fab_send);
        recyclerView = findViewById(R.id.recyclerview);

        db = FirebaseDatabase.getInstance().getReference();
        root = FirebaseDatabase.getInstance().getReference();

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        Userbase dao = new Userbase("User");


        String uId = user.getUid();
        String uEmail = user.getEmail();
        timeStamp = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewPostDiaglog();
            }
        });


        adapter = new NewRecyclerViewAdapter(this, list, mList);
        LinearLayoutManager llm = new LinearLayoutManager(this, RecyclerView.VERTICAL, true);
        llm.setStackFromEnd(true);
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

        root.child("Image").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                mList.clear();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    System.out.println("HELLOOOOO FROM");
                    Model model = snap.getValue(Model.class);
                    adapter.addPost(model);
                }
            }

            //TODO
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

    private void receiveImages(){

        root.child("Image").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                mList.clear();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    System.out.println("HELLOOOOO FROM");
                    Model model = snap.getValue(Model.class);
                    adapter.addPost(model);
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
        uploadbtn = (Button) contactPopupView.findViewById(R.id.uploadbtn);
        firebaseimage = (ImageView) contactPopupView.findViewById(R.id.firebaseimage);




        dialogbuilder.setView(contactPopupView);
        dialog = dialogbuilder.create();
        dialog.show();


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
                if (imageUri != null){
                    uploadToFirebase(imageUri);
                }
                else{
                    Model model = new Model("https://firebasestorage.googleapis.com/v0/b/penajam-b.appspot.com/o/Screen%20Shot%202022-07-22%20at%2002.32.43.png?alt=media&token=a172ba22-02cf-434d-8784-47ab2a7eaf83");
                    String modelId = db.push().getKey();
                    db.child("Image").child(modelId).setValue(model);

                }

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User");
                FirebaseAuth auth = FirebaseAuth.getInstance();
                FirebaseUser userr = auth.getCurrentUser();
                ArrayList<User> users = new ArrayList<>();

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
                                newtimer.start();
                                String pIU = u.getImageUri();
                                String uName = u.getRealname();
                                String ttl = title.getEditText().getText().toString();
                                String loc = location.getEditText().getText().toString();
                                String msg = description.getEditText().getText().toString();

                                pst = new Post(pIU, uName, ttl, loc, msg, timeStamp);


                                db.child("Posts").push().setValue(pst).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        title.getEditText().setText("");
                                        location.getEditText().setText("");
                                        if (description != null)
                                            description.getEditText().setText("");

                                    }

                                });
                            }

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                dialog.dismiss();
            }
        });

        uploadbtn.setOnClickListener(new View.OnClickListener() {
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

                        Model model = new Model(uri.toString());
                        String modelId = db.push().getKey();
                        db.child("Image").child(modelId).setValue(model);


                        Toast.makeText(NewPostActivity.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
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

                Toast.makeText(NewPostActivity.this, "Uploading Failed !!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getFileExtension(Uri mUri){

        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));

    }

    public void goToProfilePage() {
        startActivity(new Intent(NewPostActivity.this, ProfilePageActivity.class));
    }

    public void goToSettings() {
        startActivity(new Intent(NewPostActivity.this, ChatActivity.class));
    }

    public void goToNewPosts() {
        startActivity(new Intent(NewPostActivity.this, NewPostActivity.class));
    }

    public void goToMainPage() {
        startActivity(new Intent(NewPostActivity.this, MainScreenActivity.class));
    }

    public void goToUsers() {
        startActivity(new Intent(NewPostActivity.this, Userlist.class));
    }


    public void goToChat() {
        startActivity(new Intent(NewPostActivity.this, ChatActivity.class));
    }
}