package com.example.chat.websocket;

public class Message {
    private String senderName;
    private String receiverName;
    private String message;
    private String date;
    private Status status;

    public Message() {
        // Constructor fără argumente
    }

    public Message(String senderName, String receiverName, String message, String date, Status status) {
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.message = message;
        this.date = date;
        this.status = status;
    }

    // Getteri
    public String getSenderName() {
        return senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }

    public Status getStatus() {
        return status;
    }

    // Setteri
    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    // Metoda toString
    @Override
    public String toString() {
        return "Message{" +
                "senderName='" + senderName + '\'' +
                ", receiverName='" + receiverName + '\'' +
                ", message='" + message + '\'' +
                ", date='" + date + '\'' +
                ", status=" + status +
                '}';
    }
}
