package com.example.penajamm.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.penajamm.MemoryData;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {
    private  List<ChatList> pchatLists;
    private final Context pcontext;
    private String userUsername;

    public ChatAdapter(List<ChatList> pchatLists, Context pcontext) {
        this.pchatLists = pchatLists;
        this.pcontext = pcontext;
        this.userUsername = MemoryData.getData(pcontext);
    }

    @NonNull
    @Override
    public ChatAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_adapter_layout,null));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.MyViewHolder holder, int position) {
        ChatList list2 = pchatLists.get(position);
        if(list2.getpusername().equals(userUsername)){
            holder.pmyLayout.setVisibility(View.VISIBLE);
            holder.poppoLayout.setVisibility(View.GONE);
            holder.pmyMessage.setText(list2.getpmessage());
            holder.pmyTime.setText(list2.getpdate()+""+list2.getptime());
        }else {
            holder.pmyLayout.setVisibility(View.GONE);
            holder.poppoLayout.setVisibility(View.VISIBLE);
            holder.poppoMessage.setText(list2.getpmessage());
            holder.poppoTime.setText(list2.getpdate()+""+list2.getptime());


        }

    }

    @Override
    public int getItemCount() {
        return pchatLists.size();
    }
    public void updateChatList(List<ChatList> pchatLists) {
        this.pchatLists = pchatLists;
    }

    static  class MyViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout poppoLayout, pmyLayout;
        private TextView poppoMessage,pmyMessage;
        private TextView poppoTime, pmyTime;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            poppoLayout = itemView.findViewById(R.id.poppoLayout);
            pmyLayout = itemView.findViewById(R.id.pmyMessage);
            poppoMessage = itemView.findViewById(R.id.poppoMessage);
            pmyMessage = itemView.findViewById(R.id.pmyMessage);
            poppoTime = itemView.findViewById(R.id.poppoMsgTime);
            pmyTime = itemView.findViewById(R.id.pmyMsgTime);
        }
    }
}
