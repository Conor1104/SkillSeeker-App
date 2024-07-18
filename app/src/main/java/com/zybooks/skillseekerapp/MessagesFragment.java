package com.zybooks.skillseekerapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.zybooks.skillseekerapp.R;

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
        View view = inflater.inflate(R.layout.fragment_messages, container, false);

        conversationTitle = view.findViewById(R.id.conversationTitle);
        messagesRecyclerView = view.findViewById(R.id.messagesRecyclerView);
        messageInput = view.findViewById(R.id.messageInput);
        sendButton = view.findViewById(R.id.sendButton);

        // Set the conversation title with the posterUserId or fetch the name based on posterUserId
        conversationTitle.setText("Conversation with: " + posterUserId);

        // Initialize the RecyclerView (set LayoutManager, Adapter, etc.)
        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        // messagesRecyclerView.setAdapter(new MessagesAdapter()); // Set your adapter here

        // Set up the send button to handle message sending
        sendButton.setOnClickListener(v -> {
            // Handle sending message
            String message = messageInput.getText().toString();
            if (!message.isEmpty()) {
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
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }
    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }
}