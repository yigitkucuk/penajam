package com.example.penajamm;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Message> list;


    public RecyclerViewAdapter(Context context, ArrayList<Message> list){
        this.context = context;
        this.list = list;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addMessage(Message message){
        list.add(message);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.message_design,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        holder.username.setText(list.get(position).getUserEmail());
        holder.message.setText(list.get(position).getMessage());
        holder.dateTime.setText(list.get(position).getDateTime());
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
        TextView message;
        TextView dateTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.user_email);
            message = itemView.findViewById(R.id.user_message);
            dateTime = itemView.findViewById(R.id.user_message_date_time);
        }
    }
}
