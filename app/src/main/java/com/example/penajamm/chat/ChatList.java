package com.example.penajamm.chat;

public class ChatList {

    private String pusername, pname, pmessage, pdate, ptime;

    public ChatList(String pusername, String pname, String pmessage, String pdate, String ptime) {
        this.pusername = pusername;
        this.pname = pname;
        this.pmessage = pmessage;
        this.pdate = pdate;
        this.ptime = ptime;
    }

    public String getpmessage() {
        return pmessage;
    }

    public String getpusername() {
        return pusername;
    }

    public String getptime() {
        return ptime;
    }

    public String getpdate() {
        return pdate;
    }

    public String getpname() {
        return pname;
    }
}
