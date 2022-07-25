package com.example.penajamm.chat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.penajamm.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Chat extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat2);

        final ImageView pbackBtn = findViewById(R.id.pbackBtn);
        final TextView pnameTV = findViewById(R.id.pname);
        final EditText pmessageEditText = findViewById(R.id.pmessageEditTxt);
        final CircleImageView pprofilePic = findViewById(R.id.pprofilePic);
        final ImageView psendBtn = findViewById(R.id.psendBtn);

        final String pgetName = getIntent().getStringExtra("pname");
        final String pgetProfilePic = getIntent().getStringExtra("pprofile_pic");

        pnameTV.setText(pgetName);
        Picasso.get().load(pgetProfilePic).into(pprofilePic);

        psendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        pbackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}