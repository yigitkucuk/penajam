package com.example.penajamm;

public class Post {
    private String userEmail;
    private String postTitle;
    private String postDescription;
    private String dateTime;
    private String postLocation;

    public Post(){

    }

    public Post(String userEmail, String postTitle, String dateTime){
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
}
