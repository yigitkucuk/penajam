package com.example.penajamm;

public class Post {
    private String userEmail;
    private String postTitle;
    private String dateTime;

    public Post(){

    }

    public Post(String userEmail, String postTitle, String dateTime){
        this.userEmail = userEmail;
        this.postTitle = postTitle;
        this.dateTime = dateTime;
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
}
