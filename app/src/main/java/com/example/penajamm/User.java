package com.example.penajamm;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable
{


    @Exclude
    private String key;
    private String email;
    private String username;
    private String realname;

    private FirebaseUser user;
    private FirebaseAuth auth;
    private ArrayList<Post> userpost;

    private int point;
    private int totalpoint = 0;
    private int number = 0;

    private String location;
    private String instruments;
    private String description;


    public User(){
    }

    public User(String email, String username, String realname)
    {
        auth = FirebaseAuth.getInstance();
        this.user = auth.getCurrentUser();
        this.email = email;
        this.username = "@" + username;
        this.realname = realname;
        this.userpost = new ArrayList<Post>();
        this.point = 0;
        this.location = "";
        this.instruments = "";
        this.description = "Description will be here";
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getInstruments() {
        return this.instruments;
    }

    public void setInstruments(String instruments) {
        this.instruments = instruments;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPoint() {
        return this.point;
    }

    public void setPoint(int p) {
        this.totalpoint += p;
        this.number ++;
        this.point = this.totalpoint / this.number;
    }

    public String getEmail(){
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void addPostForUser(Post post) {
        this.userpost.add(post);
    }

    public FirebaseUser getUser() {
        return user;
    }

    public void setUser(FirebaseUser user) {
        this.user = user;
    }

    public String getName()
    {
        return this.username;
    }

    public void setName(String name)
    {
        this.username = name;
    }

    public String getRealname()
    {
        return this.realname;
    }

    public void setRealname(String realname)
    {
        this.realname = realname;
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    @Override
    public boolean equals(Object o) {
        User user1 = (User) o;
        if (this.user == user1.getUser())
            return true;
        return false;
    }

    /*
    public boolean isEqual(String email) {

        return this.getUser().getEmail().equals(email);
    }

     */


}