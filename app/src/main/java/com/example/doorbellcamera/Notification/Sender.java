package com.example.doorbellcamera.Notification;

public class Sender {
    private String data;
    private String to;

    public Sender(String data, String to) {
        this.data = data;
        this.to = to;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
