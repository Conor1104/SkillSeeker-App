package com.zybooks.skillseekerapp.data.model;

import java.sql.Timestamp;
import java.util.List;

public class ChatSpace {
    String chatSpace;
    List<String> userIDs;
    Timestamp lastMessageTimestamp;
    String lastMessageSenderId;

    public ChatSpace() {
    }

    public ChatSpace(String chatSpace, List<String> userIDs, Timestamp lastMessageTimestamp, String lastMessageSenderId) {
        this.chatSpace = chatSpace;
        this.userIDs = userIDs;
        this.lastMessageTimestamp = lastMessageTimestamp;
        this.lastMessageSenderId = lastMessageSenderId;
    }

    public void setChatSpace(String chatSpace) {
        this.chatSpace = chatSpace;
    }

    public void setUserIDs(List<String> userIDs) {
        this.userIDs = userIDs;
    }

    public void setLastMessageTimestamp(Timestamp lastMessageTimestamp) {
        this.lastMessageTimestamp = lastMessageTimestamp;
    }

    public void setLastMessageSenderId(String lastMessageSenderId) {
        this.lastMessageSenderId = lastMessageSenderId;
    }
}

