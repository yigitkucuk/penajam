package com.example.penajamm.messages;

public class MessagesList {

    private String pname;
    private String pusername;
    private String plastMessage;
    private String pprofilePic;
    private String pchatKey;

    private int punseenMessages;

    public MessagesList(String pname, String pusername, String plastMessage, String pprofilePic, int punseenMessages, String pchatKey) {
        this.pname = pname;
        this.pusername = pusername;
        this.plastMessage = plastMessage;
        this.pprofilePic = pprofilePic;
        this.punseenMessages = punseenMessages;
        this.pchatKey = pchatKey;
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

    public String getpchatKey() {
        return pchatKey;
    }
}
