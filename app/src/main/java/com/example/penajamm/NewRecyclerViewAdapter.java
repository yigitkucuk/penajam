package com.example.penajamm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
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

public class NewRecyclerViewAdapter extends RecyclerView.Adapter<NewRecyclerViewAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Post> list;
    private ArrayList<Model> mList;


    public NewRecyclerViewAdapter(Context context, ArrayList<Post> list, ArrayList<Model> mList){
        this.context = context;
        this.list = list;
        this.mList = mList;
    }

    public void addPost(Post post){
        list.add(post);
        notifyDataSetChanged();
    }

    public void addPost(Model model){
        mList.add(model);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NewRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.new_post_design,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewRecyclerViewAdapter.ViewHolder holder, int position) {
        Post post = list.get(position);
        holder.username.setText(list.get(position).getUserEmail());
        holder.postTitle.setText(list.get(position).getPostTitle());
        holder.postLocation.setText(list.get(position).getPostLocation());
        holder.postDescription.setText(list.get(position).getPostDescription());
        holder.dateTime.setText(list.get(position).getDateTime());

        if(mList.size() != 0 ){
            Glide.with(context).load(mList.get(position).getImageUri()).into(holder.photo);

            Glide.with(context).load(list.get(position).getImageUrl()).into(holder.profileicon);
        }


        holder.sendMessage.setOnClickListener(new View.OnClickListener() {
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
                                String userOneName = usertmp.getRealname();
                                String userOnePPUri = usertmp.getImageUri();
                                String userTwoName = post.getUserEmail();
                                String userTwoPPUri = post.getProfileImageUrl();
                                String postTitle = post.getPostTitle();
                                String chatroomName = "Chatroom: " + usertmp.getRealname() + post.getUserEmail();
                                db.child("Chatrooms").child("Chatroom: " + user.getUid() + post.getUserEmail()).child("Messages");
                                Chat chat = new Chat(chatroomName, userOneName, userTwoName, userOnePPUri, userTwoPPUri, postTitle);
                                databaseReference.push().setValue(chat);

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
        if (list == null){
            return 0;
        }
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView username;
        TextView postTitle;
        TextView postDescription;
        TextView dateTime;
        TextView postLocation;
        ImageView photo;
        ImageView profileicon;
        FloatingActionButton sendMessage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.user_name);
            postTitle = itemView.findViewById(R.id.user_title);
            postLocation = itemView.findViewById(R.id.user_point);
            postDescription = itemView.findViewById(R.id.user_message);
            dateTime = itemView.findViewById(R.id.user_message_date_time);
            photo = (ImageView) itemView.findViewById(R.id.photo);
            profileicon = (ImageView) itemView.findViewById(R.id.profile_icon);
            sendMessage = (FloatingActionButton) itemView.findViewById(R.id.sendmessagebutton);

        }
    }
}

/*                             if (usertmp.getEmail().equals(user.getEmail()) ) {

                                DatabaseReference rootRef2 = FirebaseDatabase.getInstance().getReference("Chat");
                                Query query2 = rootRef2.orderByKey().limitToLast(20);
                                query2.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {

                                            System.out.println(childSnapshot.getKey());
                                            String modelId = childSnapshot.getKey();
                                            Chat chattmp = childSnapshot.getValue(Chat.class);

                                            String userOneName = usertmp.getRealname();
                                            String userOnePPUri = usertmp.getImageUri();
                                            String userTwoName = post.getUserEmail();
                                            String userTwoPPUri = post.getProfileImageUrl();
                                            String chatroomName = "Chatroom: " + usertmp.getRealname() + post.getUserEmail();
                                            String chatroomName2 = "Chatroom: " + post.getUserEmail() + usertmp.getRealname();

                                            if( ((userOneName.equals(chattmp.getUserOneName()) || userOneName.equals(chattmp.getUserTwoName())) && (userTwoName.equals(chattmp.getUserOneName()) || userTwoName.equals(chattmp.getUserTwoName())))  ) {

                                                db.child("Chatrooms").child(chatroomName2).child("Messages");
                                                Chat chat = new Chat(chatroomName2, userOneName, userTwoName, userOnePPUri, userTwoPPUri);
                                                databaseReference.push().setValue(chat);
                                            }

                                            else {
                                                db.child("Chatrooms").child(chatroomName).child("Messages");
                                                Chat chat = new Chat(chatroomName, userOneName, userTwoName, userOnePPUri, userTwoPPUri);
                                                databaseReference.push().setValue(chat);
                                            }

                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        //chatroomName2.equals(chattmp.getChatRoomName()
                                    }
                                });

                            }*/