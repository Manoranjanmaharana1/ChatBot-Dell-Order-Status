package com.example.chatbot;

public class Chat {
    private String text;
    private int userType;
    //private int continued;

    public Chat(String text, int userType) {
        this.text = text;
        this.userType = userType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }
}
