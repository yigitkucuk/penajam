package com.example.penajamm;

import android.net.Uri;

public class Post {
    private String userEmail;
    private String postTitle;
    private String postDescription;
    private String dateTime;
    private String postLocation;
    private Uri photo;
    private String imageUrl;
    public Post(){

    }

    public Post(String imageUrl){
        this.imageUrl = imageUrl;
    }
    public Post(String userEmail, String postTitle, String postLocation, String postDescription, String dateTime){
        this.userEmail = userEmail;
        this.postTitle = postTitle;
        this.dateTime = dateTime;
        this.postDescription = postDescription;
        this.postLocation = postLocation;
        setImageUrl();

    }

    public Post(String userEmail, String postTitle, String postLocation, String postDescription, String dateTime, String imageUrl){
        this.userEmail = userEmail;
        this.postTitle = postTitle;
        this.dateTime = dateTime;
        this.postDescription = postDescription;
        this.postLocation = postLocation;
        this.imageUrl = imageUrl;
        setImageUrl(imageUrl);

    }

    public Post(String userEmail, String postTitle, String postLocation, String postDescription, String dateTime, Uri photo){
        this.userEmail = userEmail;
        this.postTitle = postTitle;
        this.dateTime = dateTime;
        this.postDescription = postDescription;
        this.postLocation = postLocation;
        setPhoto(photo);

    }

    public String getImageUrl() {
        return imageUrl;
    }
    private void setImageUrl() {
        this.imageUrl = "";
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    private void setPhoto() {
        this.photo = null;
    }

    public void setPhoto(Uri photo) {
        this.photo = photo;
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
