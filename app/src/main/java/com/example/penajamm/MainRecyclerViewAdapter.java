package com.example.penajamm;

import android.content.Context;
import android.content.Intent;
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

public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.MyViewHolder> {
    Context context;
    ArrayList<Chat> list;

    public MainRecyclerViewAdapter(Context context, ArrayList<Chat> list) {
        this.context = context;
        this.list = list;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.new_message_design,parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Chat chat = list.get(position);
        holder.realname.setText(chat.getUserOneName());
        holder.title.setText("Related Post Title: " + chat.getPosttitle());
        Glide.with(context).load(chat.getUserOnePPUri()).into(holder.profileicon);
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

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
                        DatabaseReference rootRef2 = FirebaseDatabase.getInstance().getReference("Chat");
                        Query query2 = rootRef2.orderByKey().limitToLast(20);
                        query2.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {

                                    System.out.println(childSnapshot.getKey());
                                    String modelId = childSnapshot.getKey();
                                    Chat chattmp = childSnapshot.getValue(Chat.class);
                                    if (chattmp.getUserTwoName().equals(usertmp.getRealname()) ) {




                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.chatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference db = FirebaseDatabase.getInstance().getReference();
                FirebaseAuth auth = FirebaseAuth.getInstance();
                FirebaseUser user = auth.getCurrentUser();

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Chat");
                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("User");
                Query query = rootRef.orderByKey().limitToLast(20);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {

                            System.out.println(childSnapshot.getKey());
                            String modelId = childSnapshot.getKey();
                            User usertmp = childSnapshot.getValue(User.class);
                            if (usertmp.getEmail().equals(user.getEmail()) ) {


                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                context.startActivity(new Intent(context, ChatActivity.class));

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView realname;
        TextView title;
        ImageView profileicon;
        FloatingActionButton chatBtn;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            realname = itemView.findViewById(R.id.user_realname);
            title = itemView.findViewById(R.id.title);
            profileicon = itemView.findViewById(R.id.profile_icon);
            chatBtn = itemView.findViewById(R.id.fab);

        }
    }



}
