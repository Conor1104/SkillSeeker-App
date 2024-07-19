package com.zybooks.skillseekerapp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessageViewHolder> {
    private List<Message> messageList;

    public MessagesAdapter(List<Message> messageList) {
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message message = messageList.get(position);
        Log.d("MessagesAdapter", "Binding message at position " + position);
        holder.bind(message);
        holder.bind(message);
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        private TextView messageContent;
        private TextView messageSender;
        private TextView messageTimestamp;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            messageContent = itemView.findViewById(R.id.messageContent);
            messageSender = itemView.findViewById(R.id.messageSender);
            messageTimestamp = itemView.findViewById(R.id.messageTimestamp);
        }

        public void bind(Message message) {
            messageContent.setText(message.getContent());
            messageSender.setText(message.getSender()); // Optional: Display sender
            // Format timestamp if needed
            messageTimestamp.setText(new SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.getDefault()).format(message.getTimestamp()));
        }
    }
}

