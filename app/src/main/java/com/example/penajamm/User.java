package com.example.penajamm;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable
{


    @Exclude
    private String key;
    private String username;
    private String realname;
    private FirebaseUser user;

    public User(){
    }

    public User(FirebaseUser user, String username, String realname)
    {
        this.user = user;
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

    @Override
    public boolean equals(Object o) {
        User user1 = (User) o;
        if (this.user == o)
            return true;
        return false;
    }

}