package com.zybooks.skillseekerapp;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.DialogInterface;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

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
    private static final String TAG = "MessagesFragment";

    private String posterUserId;
    private TextView conversationTitle;
    private RecyclerView messagesRecyclerView;
    private EditText messageInput;
    private Button sendButton;
    private Button startConversationButton;

    private List<Message> messageList;
    private MessagesAdapter messagesAdapter;

    private FirebaseFirestore db;
    private String currentUserEmail;
    private String receiverUserEmail;

    public MessagesFragment() {
        // Required empty public constructor
    }

    public static MessagesFragment newInstance(String receiverUserEmail) {
        MessagesFragment fragment = new MessagesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, receiverUserEmail);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            receiverUserEmail = getArguments().getString(ARG_PARAM1);
        }
        currentUserEmail = getCurrentUserEmail();
        db = FirebaseFirestore.getInstance(); // Initialize FirebaseFirestore
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("MessagesFragment", "onCreateView called");
        View view = inflater.inflate(R.layout.fragment_messages, container, false);

        if (view == null) {
            Log.e(TAG, "Failed to inflate layout");
            return null;
        }

        conversationTitle = view.findViewById(R.id.conversationTitle);
        messagesRecyclerView = view.findViewById(R.id.messagesRecyclerView);
        messageInput = view.findViewById(R.id.messageInput);
        sendButton = view.findViewById(R.id.sendButton);
        startConversationButton = view.findViewById(R.id.startConversationButton);

        if (conversationTitle == null || messagesRecyclerView == null ||
                messageInput == null || sendButton == null || startConversationButton == null) {
            Log.e(TAG, "One or more views not found");
        }

        messageList = new ArrayList<>();
        messagesAdapter = new MessagesAdapter(messageList);
        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        messagesRecyclerView.setAdapter(messagesAdapter);

        sendButton.setOnClickListener(v -> sendMessage());
        startConversationButton.setOnClickListener(v -> showStartConversationDialog());


        loadMessages();

        return view;
    }

    private void loadMessages() {
        if (db != null) {
            Log.d("MessagesFragment", "Loading messages...");
            db.collection("messages")
                    .whereEqualTo("sender", currentUserEmail)
                    .whereEqualTo("receiver", receiverUserEmail)
                    .orderBy("timestamp", Query.Direction.ASCENDING)
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        messageList.clear();
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            Message message = document.toObject(Message.class);
                            messageList.add(message);
                        }
                        messagesAdapter.notifyDataSetChanged();
                    })
                    .addOnFailureListener(e -> {
                        Log.e("MessagesFragment", "Error loading messages", e);
                    });
        } else {
            Log.e("MessagesFragment", "FirebaseFirestore instance is null");
        }
    }

    private void sendMessage() {
        String content = messageInput.getText().toString();
        if (!content.isEmpty()) {
            Message message = new Message(currentUserEmail, content, new Date(), receiverUserEmail);
            db.collection("messages")
                    .add(message)
                    .addOnSuccessListener(documentReference -> {
                        messageList.add(message);
                        messagesAdapter.notifyDataSetChanged();
                        messageInput.setText("");
                        messagesRecyclerView.smoothScrollToPosition(messageList.size() - 1);
                    })
                    .addOnFailureListener(e -> {
                        Log.e("MessagesFragment", "Error sending message", e);
                    });
        }
    }

    private void showStartConversationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_start_conversation, null);
        EditText userEmailInput = dialogView.findViewById(R.id.userEmailInput);
        EditText messageInput = dialogView.findViewById(R.id.messageInput);
        Button sendButton = dialogView.findViewById(R.id.sendButton);

        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        sendButton.setOnClickListener(v -> {
            String userEmail = userEmailInput.getText().toString();
            String messageContent = messageInput.getText().toString();
            if (!userEmail.isEmpty() && !messageContent.isEmpty()) {
                sendMessageToUser(userEmail, messageContent);
                dialog.dismiss();
            } else {
                Toast.makeText(getContext(), "Please enter both email and message", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }

    private void sendMessageToUser(String recipientEmail, String messageContent) {
        Message message = new Message(currentUserEmail, messageContent, new Date(), recipientEmail);
        db.collection("messages")
                .add(message)
                .addOnSuccessListener(documentReference -> {
                    // Handle success, e.g., update the UI or notify the user
                })
                .addOnFailureListener(e -> {
                    Log.e("MessagesFragment", "Error sending message", e);
                });
    }

    private String getCurrentUserEmail() {
        // Implement method to get the current logged-in user's email
        return "user@example.com"; // Placeholder for current user email
    }
}
























    // Returns the fragment_messages xml file. not the fragment_chat xml file that one is view2
        //Commented out the original code, regardless we might have to change it entirely if not just erase my code and reactive your old code
        //*/

        //return view;

        // Set the conversation title with the posterUserId or fetch the name based on posterUserId
//        conversationTitle.setText("Conversation with: " + posterUserId);

        /*
        //Initialize the RecyclerView (set LayoutManager, Adapter, etc.)
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

        //});
        //return view;

        private void searchUser(String userId){
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

        private void sendMessage (String content){
            if (posterUserId == null) {
                Toast.makeText(getContext(), "Please search and select a user first", Toast.LENGTH_SHORT).show();
                return view;
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

        private String getConversationId (String userId){
            // Generate a unique conversation ID based on the two user IDs
            String currentUserId = "YourSenderId"; // Replace with actual sender ID
            return currentUserId.compareTo(userId) < 0 ? currentUserId + "_" + userId : userId + "_" + currentUserId;
        }


        private void loadMessages (String userId){
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
    */ //return view;}

