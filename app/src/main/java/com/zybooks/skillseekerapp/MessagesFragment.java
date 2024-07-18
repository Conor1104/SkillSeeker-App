package com.zybooks.skillseekerapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

//import android.os.Message;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.zybooks.skillseekerapp.Message;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
    private EditText userIdInput;
    private Button searchUserButton;

    private String sender;
    private String content;
    private Date timestamp;

    private List<Message> messageList;
    private MessagesAdapter messagesAdapter;
    private SSDataBaseHelper dbHelper;

    public MessagesFragment() {
        // Required empty public constructor
    }

    public static MessagesFragment newInstance(String param1, String param2) {
        MessagesFragment fragment = new MessagesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new SSDataBaseHelper();
//        if (getArguments() != null) {
//            posterUserId = getArguments().getString("posterUserId");
//        }
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
        userIdInput = view.findViewById(R.id.userIdInput);
        searchUserButton = view.findViewById(R.id.searchUserButton);

        // Set the conversation title with the posterUserId or fetch the name based on posterUserId
//        conversationTitle.setText("Conversation with: " + posterUserId);

        // Initialize the RecyclerView (set LayoutManager, Adapter, etc.)
        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        // messagesRecyclerView.setAdapter(new MessagesAdapter()); // Set your adapter here
        messagesRecyclerView.setAdapter(messagesAdapter);

        searchUserButton.setOnClickListener(v -> {
            String userId = userIdInput.getText().toString().trim();
            if (!userId.isEmpty()) {
                searchUser(userId);
            } else {
                Toast.makeText(getContext(), "Please enter a UserId", Toast.LENGTH_SHORT).show();
            }
        });

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

    private void searchUser(String userId) {
        dbHelper.getDb().collection("users").document(userId).get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                posterUserId = userId;
                conversationTitle.setText("Conversation with: " + userId);
                loadMessages(userId);
            } else {
                Toast.makeText(getContext(), "User not found", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e -> {
            Toast.makeText(getContext(), "Search failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() instanceof AppCompatActivity) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (getActivity() instanceof AppCompatActivity) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        }
    }

    private void sendMessage(String content) {
        if (posterUserId == null) {
            Toast.makeText(getContext(), "Please search and select a user first", Toast.LENGTH_SHORT).show();
            return;
        }
        // Create a new Message object
        Message message = new Message();
        message.setContent(content);
        // Set other message details like sender, timestamp, etc.
        message.setSender("YourSenderId");
        message.setReceiver(posterUserId);
        message.setTimestamp(new Date());

        // Add message to the messageList
        dbHelper.getDb().collection("messages").add(message).addOnSuccessListener(documentReference -> {
            messageList.add(message);
        messageList.add(message);
        messagesAdapter.notifyItemInserted(messageList.size() - 1);
        messagesRecyclerView.scrollToPosition(messageList.size() - 1);
        });
    }

    private String getConversationId(String userId) {
        // Generate a unique conversation ID based on the two user IDs
        String currentUserId = "YourSenderId"; // Replace with actual sender ID
        return currentUserId.compareTo(userId) < 0 ? currentUserId + "_" + userId : userId + "_" + currentUserId;
    }


    private void loadMessages(String userId) {
        dbHelper.getDb().collection("messages")
                .whereEqualTo("conversationId", getConversationId(userId))
                .orderBy("timestamp", Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        messageList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Message message = document.toObject(Message.class);
                            messageList.add(message);
                        }
                        messagesAdapter.notifyDataSetChanged();
                        messagesRecyclerView.scrollToPosition(messageList.size() - 1);
                    }
                });

    }
}