package com.example.penajamm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
    Context context;
    ArrayList<User> list;

    public MyAdapter(Context context, ArrayList<User> list) {
        this.context = context;
        this.list = list;
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
        holder.point.setText(""+ user.getPoint());
        holder.description.setText(user.getDescription());
        Glide.with(context).load(user.getImageUri()).into(holder.profileicon);

        if(user.getVideoUri()!=null) {
            MediaController mediaController = new MediaController(context);
            holder.videoView.setMediaController(mediaController);
            mediaController.setAnchorView(holder.videoView);
            holder.videoView.setVisibility(View.VISIBLE);
            holder.videoView.setVideoPath(user.getVideoUri());
        }
        else
            holder.videoView.setVisibility(View.INVISIBLE);

        holder.pointBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
        VideoView videoView;
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
            videoView = itemView.findViewById(R.id.videoView2);
            point = itemView.findViewById(R.id.fab2);

        }
    }
}
