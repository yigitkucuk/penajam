package com.example.penajamm;

public class Users {


    private String name;
    private String realname;

    public Users() {

    }

    public Users( String name, String realname) {

        this.name = name;
        this.realname = realname;
    }



    public String getName() {
        return name;
    }

    public String getRealname() {
        return realname;
    }
}
