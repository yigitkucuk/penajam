package com.example.penajamm;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable, Comparable
{

    @Exclude
    private String key;
    private String email;
    private String username;
    private String realname;

    private FirebaseUser user;
    private FirebaseAuth auth;
    private ArrayList<Post> userpost;

    private double point;
    private double totalpoint;
    private int number;
    private String imageUri;
    private String videoUri;
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
        this.totalpoint = 0;
        this.number = 0;
        this.imageUri = "";
        this.videoUri = "";
        this.location = "";
        this.instruments = "";
        this.description = "Description will be here";
    }

    public String getVideoUri() {
        return videoUri;
    }

    public void setVideoUri(String videoUri) {
        this.videoUri = videoUri;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
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

    public double getPoint() {
        return this.point;
    }

    public void setPoint() {
        this.point = this.totalpoint / this.number;
    }

    public double getTotalpoint() {
        return this.totalpoint;
    }

    public void setTotalpoint(double totalpoint) {
        this.totalpoint += totalpoint;
    }

    public void setRatingNum(int num) {
        this.number += num;
    }

    public float getRatingNum() {
        return this.number;
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
        if (this.user == user1.getUser()){
            return true;
        }

        return false;
    }

    @Override
    public int compareTo(Object o) {
        if(this.getPoint() > ((User) o).getPoint()){
            return 1;
        }else{
            return -1;
        }

    }

    /*
    public boolean isEqual(String email) {

        return this.getUser().getEmail().equals(email);
    }

     */


}