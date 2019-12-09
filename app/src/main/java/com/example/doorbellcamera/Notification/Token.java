package com.example.doorbellcamera.Notification;

public class Token {
    private String token;

    public Token(String token){
        this.setToken(token);
    }
    public Token(){}

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
