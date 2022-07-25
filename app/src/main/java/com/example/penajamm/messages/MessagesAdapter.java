package com.example.penajamm.messages;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.penajamm.R;
import com.example.penajamm.chat.Chat;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MyViewHolder> {

    private List<MessagesList> pmessagesLists;
    private final Context pcontext;

    public MessagesAdapter(List<MessagesList> pmessagesLists, Context pcontext) {
        this.pmessagesLists = pmessagesLists;
        this.pcontext = pcontext;
    }

    @NonNull
    @Override
    public MessagesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.messages_adapter_layout,null));
    }

    @Override
    public void onBindViewHolder(@NonNull MessagesAdapter.MyViewHolder holder, int position) {

        MessagesList list2 = pmessagesLists.get(position);

        if(!list2.getpprofilePic().isEmpty()) {
            Picasso.get().load(list2.getpprofilePic()).into(holder.pprofilePic);
        }

        holder.pname.setText(list2.getpname());
        holder.plastMessage.setText(list2.getplastMessage());

        if(list2.getpunseenMessages() == 0) {
            holder.punseenMessages.setVisibility(View.GONE);
            holder.plastMessage.setTextColor(Color.parseColor("#959595"));
        }

        else {
            holder.punseenMessages.setVisibility(View.VISIBLE);
            holder.punseenMessages.setText(list2.getpunseenMessages()+"");

            //It was theme_color_80 I changed it to purple.
            holder.plastMessage.setTextColor(pcontext.getResources().getColor(R.color.purple_200));

        }

        holder.prootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(pcontext, Chat.class);
                intent.putExtra("username",list2.getpusername());
                intent.putExtra("name",list2.getpname());
                intent.putExtra("profile_pic", list2.getpprofilePic());
                intent.putExtra("chat_key", list2.getpchatKey());

                pcontext.startActivity(intent);

            }
        });

    }
    public void updateData(List<MessagesList> pmessagesLists) {
        this.pmessagesLists= pmessagesLists;
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {

        return pmessagesLists.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView pprofilePic;
        private TextView pname;
        private TextView plastMessage;
        private TextView punseenMessages;
        private LinearLayout prootLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            pprofilePic = itemView.findViewById(R.id.pprofilePic);
            pname = itemView.findViewById(R.id.pname);
            plastMessage = itemView.findViewById(R.id.plastMessage);
            punseenMessages = itemView.findViewById(R.id.punseenMessages);
            prootLayout = itemView.findViewById(R.id.prootLayout);
        }
    }
}
