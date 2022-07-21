package com.example.penajamm;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;



public class PostActivity extends AppCompatActivity {
    Button selectImageBtn;
    ImageView imageView;
    private ImageButton backbtn, btnAssig, btnMainScreen, btnProfile ;


    static final int SELECT_IMAGE = 12;
    Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        backbtn = findViewById(R.id.backbtn);
        btnAssig = findViewById(R.id.btn_Assig);
        btnMainScreen = findViewById(R.id.btn_MainScreen);
        btnProfile = findViewById(R.id.btn_Profile);


        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goProfile(); }
        });

        btnAssig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goAssig(); }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goAssig();
            }
        });

        btnMainScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goMain();
            }
        });

        selectImageBtn = findViewById(R.id.edit_profile);
        imageView = findViewById(R.id.profile_icon);
        selectImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, SELECT_IMAGE);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_IMAGE && resultCode == RESULT_OK) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);

        }
        else if (resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "picture selection canceled", Toast.LENGTH_SHORT ).show();
        }
    }

    public void goMain() {
        startActivity(new Intent(PostActivity.this, MainScreenActivity.class));
    }

    public void goAssig() {
        startActivity(new Intent(PostActivity.this, ScrollingActivity.class));
    }

    public void goProfile() {
        startActivity(new Intent(PostActivity.this, ProfilePageActivity.class));
    }

    public void goSett() {
        startActivity(new Intent(PostActivity.this, SettingsActivity.class));
    }


}

    /*private void uploadToFirebase(Uri uri) {
        StorageReference fileRef = storageReference.child(System.currentTimeMillis() + "." + getFileExtention(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        pst.setImageUrl(uri.toString());
                        String modelID = db.push().getKey();
                        db.child("Posts").push().setValue(pst).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                title.getEditText().setText("");
                                location.getEditText().setText("");
                                if (description != null)
                                    description.getEditText().setText("");

                            }

                        });

                        Toast.makeText(NewPostActivity.this, "Uploaded successfully!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                progressBar.setVisibility(View.VISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(NewPostActivity.this, "Uploading failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getFileExtention(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }
    ////////////////////// */