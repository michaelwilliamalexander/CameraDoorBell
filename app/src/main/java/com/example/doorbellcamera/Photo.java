package com.example.doorbellcamera;

public class Photo  {
    private String path;

    public Photo(){
        //empty constructor needed
    }

    public Photo(String path){
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
