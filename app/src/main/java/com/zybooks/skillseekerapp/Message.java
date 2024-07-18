package com.zybooks.skillseekerapp;

import java.util.Date;


public class Message {
    private String sender;
    private String content;
    private Date timestamp;
    private String receiver;

    public Message() {
        // Default constructor
    }

    public Message(String sender, String content, Date timestamp, String receiver) {
        this.sender = sender;
        this.content = content;
        this.timestamp = timestamp;
        this.receiver = receiver;
    }

    // Getters and setters
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
}

