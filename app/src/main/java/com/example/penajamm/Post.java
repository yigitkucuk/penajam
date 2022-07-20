package com.example.penajamm;

import android.graphics.drawable.Drawable;

public class Post {
    private String userEmail;
    private String postTitle;
    private String postDescription;
    private String dateTime;
    private String postLocation;


    public Post(){

    }

    public Post(String userEmail, String postTitle, String postLocation, String postDescription, String dateTime){
        this.userEmail = userEmail;
        this.postTitle = postTitle;
        this.dateTime = dateTime;
        this.postDescription = postDescription;
        this.postLocation = postLocation;

    }

    public String getUserEmail(){
        return userEmail;
    }

    public String getDateTime(){
        return dateTime;
    }

    public String getPostTitle(){
        return postTitle;
    }

    public String getPostDescription(){
        return postDescription;
    }

    public String getPostLocation(){
        return postLocation;
    }

    //public Drawable getPhoto() { return photo; }
}
