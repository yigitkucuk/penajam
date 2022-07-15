package com.example.penajamm;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class User implements Serializable
{


    @Exclude
    private String key;
    private String username;
    private String realname;

    public User(){

    }

    public User(String username, String realname)
    {
        this.username = username;
        this.realname = realname;
    }

    public String getName()
    {
        return username;
    }

    public void setName(String name)
    {
        this.username = name;
    }

    public String getRealname()
    {
        return realname;
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
}