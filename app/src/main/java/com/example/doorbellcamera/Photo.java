package com.example.doorbellcamera;

public class Photo  {
    private String photo;
    private String timestamp;

    public Photo(){
        //empty constructor needed
    }

    public Photo(String photo, String timestamp){
        this.photo = photo;
        this.timestamp = timestamp;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
