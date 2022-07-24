package com.example.penajamm;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.penajamm.databinding.ActivityProfilePageBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.snackbar.Snackbar;
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

public class ProfilePageActivity extends AppCompatActivity implements Navigation{

    private ActivityProfilePageBinding binding;
    private ImageButton btnSettings, btnMainScreen, btnAssig, btnBack;
    private VideoView videoView;
    private Button uploadBtn, sendBtn;
    Uri imageUri;
    Userbase dao = new Userbase("User");
    private TextView textView, usernameView, locationView, pointView, descriptionView, instrumentsView;
    private ArrayList<User> list;
    private ShapeableImageView profileicon;

    private StorageReference reference = FirebaseStorage.getInstance().getReference();

    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference usersRef = rootRef.child("User");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityProfilePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);

        Userbase userbase = new Userbase("User");

        list = userbase.getUsers();
        for (User u: list) {
            System.out.println(u.getName());
        }

        FloatingActionButton fab = binding.fab;
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

        btnSettings = findViewById(R.id.btn_Settings);
        btnMainScreen = findViewById(R.id.btn_MainScreen);
        btnAssig = findViewById(R.id.btn_Assig);
        btnBack = findViewById(R.id.backbtn);
        videoView = findViewById(R.id.videoView);

        //String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.video;
        //Uri uri = Uri.parse(videoPath);
        //videoView.setVideoURI(uri);

        uploadBtn = (Button) findViewById(R.id.uploadbtn);
        sendBtn = (Button) findViewById(R.id.sendbtn);

        textView = (TextView) findViewById(R.id.name);
        usernameView = (TextView) findViewById(R.id.username);
        instrumentsView = (TextView) findViewById(R.id.instrumentsView);
        locationView = (TextView) findViewById(R.id.name2);
        pointView = (TextView) findViewById(R.id.pointView);
        descriptionView = (TextView) findViewById(R.id.descriptionView);
        profileicon = (ShapeableImageView) findViewById(R.id.profile_icon);


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

                        Glide.with(ProfilePageActivity.this).load(u.getImageUri()).into(profileicon);
                        //Glide.with(ProfilePageActivity.this).load(u.getVideoUri()).into(videoView);
                        Uri videoUri = Uri.parse(u.getImageUri());
                        videoView.setVideoURI(videoUri);
                        textView.setText(u.getRealname());
                        usernameView.setText(u.getName());
                        locationView.setText(u.getLocation());
                        instrumentsView.setText(u.getInstruments());
                        pointView.setText("" + u.getPoint());
                        descriptionView.setText(u.getDescription());
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);

        btnBack.setOnClickListener(view -> goToMainPage());

        btnSettings.setOnClickListener(view -> goToSettings());

        btnMainScreen.setOnClickListener(view -> goToMainPage());

        btnAssig.setOnClickListener(view -> goToNewPosts());

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("video/*");

                startActivityForResult(galleryIntent , 2);

                /* else{
                    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("User");
                    Query query = rootRef.orderByKey().limitToLast(1);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {

                                System.out.println(childSnapshot.getKey());
                                String modelId = childSnapshot.getKey();
                                User usertmp =  childSnapshot.getValue(User.class);
                                usertmp.setVideoUri("");
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

                } */
            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (imageUri != null)
                    uploadToFirebase(imageUri);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode ==2 && resultCode == RESULT_OK && data != null){

            imageUri = data.getData();
            videoView.setVideoURI(imageUri);

        }
    }

    private void uploadToFirebase(Uri uri){
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final StorageReference fileRef = reference.child("/videos/" + uid+ "/" + uri.toString());
        fileRef.putFile(uri);

        if(uri != null){
            UploadTask uploadTask = fileRef.putFile(uri);
            uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if(task.isSuccessful()) {
                        Toast.makeText(ProfilePageActivity.this, "Video Upload Completed", Toast.LENGTH_SHORT).show();
                        System.out.println("YEEEEEEEEEES");
                        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("User");
                        Query query = rootRef.orderByKey().limitToLast(9);
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {

                                    System.out.println(childSnapshot.getKey());
                                    String modelId = childSnapshot.getKey();
                                    User usertmp =  childSnapshot.getValue(User.class);
                                    if (usertmp.getEmail().equals( FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
                                        usertmp.setVideoUri(uri.toString());
                                        dao.update2(modelId, usertmp);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        Toast.makeText(ProfilePageActivity.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                }
            });
        }

        else {
            Toast.makeText(ProfilePageActivity.this, "upload failed!", Toast.LENGTH_SHORT).show();
        }

    }

    private String getFileExtension(Uri mUri){

        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));

    }

    public void goToProfilePage() {
        startActivity(new Intent(ProfilePageActivity.this, ProfilePageActivity.class));
    }

    public void goToSettings() {
        startActivity(new Intent(ProfilePageActivity.this, SettingsActivity.class));
    }

    public void goToNewPosts() {
        startActivity(new Intent(ProfilePageActivity.this, NewPostActivity.class));
    }

    public void goToMainPage() {
        startActivity(new Intent(ProfilePageActivity.this, MainScreenActivity.class));
    }

    public void goToUsers() {
        startActivity(new Intent(ProfilePageActivity.this, Userlist.class));
    }

}