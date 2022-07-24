package com.example.penajamm;

public class MessagesList {

    private String pname;
    private String pusername;
    private String plastMessage;
    private String pprofilePic;

    private int punseenMessages;

    public MessagesList(String pname, String pusername, String plastMessage, String pprofilePic, int punseenMessages) {
        this.pname = pname;
        this.pusername = pusername;
        this.plastMessage = plastMessage;
        this.pprofilePic = pprofilePic;
        this.punseenMessages = punseenMessages;

    }

    public String getpname() {
        return pname;
    }

    public String getpusername() {
        return pusername;
    }

    public String getplastMessage() {
        return plastMessage;
    }

    public String getpprofilePic() {
        return pprofilePic;
    }

    public int getpunseenMessages() {
        return punseenMessages;
    }
}
