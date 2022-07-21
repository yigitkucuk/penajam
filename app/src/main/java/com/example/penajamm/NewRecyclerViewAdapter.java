package com.example.penajamm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

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
        holder.username.setText(list.get(position).getUserEmail());
        holder.postTitle.setText(list.get(position).getPostTitle());
        holder.postLocation.setText(list.get(position).getPostLocation());
        holder.postDescription.setText(list.get(position).getPostDescription());
        holder.dateTime.setText(list.get(position).getDateTime());
        Glide.with(context).load(mList.get(position).getImageUri()).into(holder.photo);

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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.user_name);
            postTitle = itemView.findViewById(R.id.user_title);
            postLocation = itemView.findViewById(R.id.user_location);
            postDescription = itemView.findViewById(R.id.user_message);
            dateTime = itemView.findViewById(R.id.user_message_date_time);
            photo = (ImageView) itemView.findViewById(R.id.photo);
        }
    }
}
