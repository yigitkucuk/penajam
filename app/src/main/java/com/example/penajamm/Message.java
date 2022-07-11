package com.example.penajamm;

public class Message {
    private String userEmail;
    private String message;
    private String dateTime;

    public Message(){

    }

    public Message(String userEmail, String message, String dateTime){
        this.userEmail = userEmail;
        this.message = message;
        this.dateTime = dateTime;
    }

    public String getUserEmail(){
        return userEmail;
    }

    public String getMessage(){
        return message;
    }

    public String getDateTime(){
        return dateTime;
    }
}
