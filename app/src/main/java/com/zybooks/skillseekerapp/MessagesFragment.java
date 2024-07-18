package com.zybooks.skillseekerapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

//import android.os.Message;
import com.zybooks.skillseekerapp.Message;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;



import com.zybooks.skillseekerapp.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MessagesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessagesFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String posterUserId;
    private TextView conversationTitle;
    private RecyclerView messagesRecyclerView;
    private EditText messageInput;
    private Button sendButton;

    private String sender;
    private String content;
    private Date timestamp;

    private List<Message> messageList;
    private MessagesAdapter messagesAdapter;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MessagesFragment() {
        // Required empty public constructor
    }

    public static MessagesFragment newInstance(String param1, String param2) {
        MessagesFragment fragment = new MessagesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            posterUserId = getArguments().getString("posterUserId");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("MessagesFragment", "onCreateView called");
        View view = inflater.inflate(R.layout.fragment_messages, container, false);

        conversationTitle = view.findViewById(R.id.conversationTitle);
        messagesRecyclerView = view.findViewById(R.id.messagesRecyclerView);
        messageInput = view.findViewById(R.id.messageInput);
        sendButton = view.findViewById(R.id.sendButton);
        messageList = new ArrayList<>();
        messagesAdapter = new MessagesAdapter(messageList);

        // Set the conversation title with the posterUserId or fetch the name based on posterUserId
        conversationTitle.setText("Conversation with: " + posterUserId);

        // Initialize the RecyclerView (set LayoutManager, Adapter, etc.)
        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        // messagesRecyclerView.setAdapter(new MessagesAdapter()); // Set your adapter here
        messagesRecyclerView.setAdapter(messagesAdapter);

        // Set up the send button to handle message sending
        sendButton.setOnClickListener(v -> {
            // Handle sending message
            String message = messageInput.getText().toString().trim();
            if (!message.isEmpty()) {
                sendMessage(message);
                // Add message to database and update RecyclerView
                // Example: addMessageToDatabase(posterUserId, message);
                messageInput.setText(""); // Clear the input field
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }

    private void sendMessage(String content) {
        // Create a new Message object
        Message message = new Message();
        message.setContent(content);
        // Set other message details like sender, timestamp, etc.
        message.setSender("YourSenderId");

        // Add message to the messageList
        messageList.add(message);
        messagesAdapter.notifyItemInserted(messageList.size() - 1);
        messagesRecyclerView.scrollToPosition(messageList.size() - 1);
    }
}