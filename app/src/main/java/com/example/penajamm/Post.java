package com.example.penajamm;

import android.net.Uri;

public class Post {
    private String userEmail;
    private String postTitle;
    private String postDescription;
    private String dateTime;
    private String postLocation;
    private Uri photo;
    private String profileImageUrl;

    public Post(){

    }

    public Post(String profileImageUrl, String userEmail, String postTitle, String postLocation, String postDescription, String dateTime){
        this.userEmail = userEmail;
        this.postTitle = postTitle;
        this.dateTime = dateTime;
        this.postDescription = postDescription;
        this.postLocation = postLocation;
        this.profileImageUrl = profileImageUrl;

    }


    public String getImageUrl() {
        return this.profileImageUrl;
    }

    private void setImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
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

    public Uri getPhoto() { return photo; }
}
