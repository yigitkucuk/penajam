package com.example.penajamm;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Formatter;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
    Context context;
    ArrayList<User> list;
    private AlertDialog.Builder dialogbuilder;
    private AlertDialog dialog;
    private int starnum;
    public MyAdapter(Context context, ArrayList<User> list) {
        this.context = context;
        this.list = list;
        this.starnum = 0;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.userlist_design,parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        User user = list.get(position);
        holder.name.setText(user.getName());
        holder.realname.setText(user.getRealname());
        holder.location.setText(user.getLocation());
        holder.instruments.setText(user.getInstruments());
        Double d = user.getPoint();
        Formatter formatter = new Formatter();
        formatter.format("%.2f", d);
        holder.point.setText(formatter.toString() );
        holder.description.setText(user.getDescription());
        Glide.with(context).load(user.getImageUri()).into(holder.profileicon);
        //if(user.getVideoUri()!=null)
        //holder.videoView.setVideoPath(user.getVideoUri());
        holder.pointBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewVideoDiaglog(user);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView realname;
        TextView location;
        TextView instruments;
        TextView point;
        TextView description;
        ImageView profileicon;
        Button pointBtn;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.user_name);
            realname = itemView.findViewById(R.id.user_realname);
            profileicon = itemView.findViewById(R.id.profile_icon);
            location = itemView.findViewById(R.id.user_location);
            instruments = itemView.findViewById(R.id.instruments);
            point = itemView.findViewById(R.id.point);
            description = itemView.findViewById(R.id.description);
            pointBtn = itemView.findViewById(R.id.fab2);

        }
    }

    public void createNewVideoDiaglog(User user) {
        dialogbuilder = new AlertDialog.Builder(context);
        final View contactPopupView = LayoutInflater.from(context).inflate(R.layout.point_design, null);
        VideoView videoView = (VideoView) contactPopupView.findViewById(R.id.videoView2);
        ImageButton backBtn = (ImageButton) contactPopupView.findViewById(R.id.fab2);
        FloatingActionButton applyBtn = (FloatingActionButton) contactPopupView.findViewById(R.id.fab);
        RatingBar ratingBar = (RatingBar) contactPopupView.findViewById(R.id.ratingBar);

        dialogbuilder.setView(contactPopupView);
        dialog = dialogbuilder.create();
        dialog.show();

        MediaController mediaController = new MediaController(context);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);
        videoView.start();



        if(user.getVideoUri()!=null)
            videoView.setVideoPath(user.getVideoUri());

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        applyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Userbase dao = new Userbase("User");
                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("User");
                Query query = rootRef.orderByKey().limitToLast(20);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {

                            System.out.println(childSnapshot.getKey());
                            String modelId = childSnapshot.getKey();
                            User usertmp = childSnapshot.getValue(User.class);
                            if (usertmp.getEmail().equals(user.getEmail()) && !(FirebaseAuth.getInstance().getCurrentUser().getEmail().equals(user.getEmail()))) {
                                if (ratingBar.getRating() != 0) {
                                    usertmp.setTotalpoint(ratingBar.getRating());
                                    usertmp.setRatingNum(1);
                                    usertmp.setPoint();
                                    dao.update2(modelId, usertmp);
                                    user.setTotalpoint(ratingBar.getRating());
                                    user.setRatingNum(1);
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                dialog.dismiss();
                context.startActivity(new Intent(context, Userlist.class));
                context.startActivity(new Intent(context, Userlist.class));
            }
        });

    }
}
