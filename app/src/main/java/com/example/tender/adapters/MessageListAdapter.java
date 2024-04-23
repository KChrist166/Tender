package com.example.tender.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.tender.R;
import com.example.tender.models.MessageItem;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.MyViewHolder> {
    private Context context;
    private List<MessageItem> messageList;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, content, count;
        ImageView thumbnail;
        RelativeLayout viewIndicator;

        MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.text_name);
            content = view.findViewById(R.id.text_content);
            thumbnail = view.findViewById(R.id.thumbnail);
            viewIndicator = view.findViewById(R.id.layout_dot_indicator);

        }
    }


    public MessageListAdapter(Context context, List<MessageItem> messageList) {
        this.context = context;
        this.messageList = messageList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_message_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final MessageItem item = messageList.get(position);
        holder.name.setText(item.getName());
        holder.content.setText(item.getContent());

        holder.viewIndicator.setVisibility(View.INVISIBLE);
//        if(item.getCount() <= 0){
//            holder.viewIndicator.setVisibility(View.INVISIBLE);
//        }
        
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

}