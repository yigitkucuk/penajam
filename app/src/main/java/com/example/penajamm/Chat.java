package com.example.penajamm;

public class Chat {

        private String chatRoomName;
        private String userOneName;
        private String userOnePPUri;
        private String userTwoName;
        private String userTwoPPUri;
        private String posttitle;

        public Chat(){

        }

        public Chat(String chatRoomName, String userOneName, String userTwoName, String userOnePPUri, String userTwoPPUri, String postTitle){
            this.chatRoomName = chatRoomName;
            this.userOneName = userOneName;
            this.userOnePPUri = userOnePPUri;
            this.userTwoName = userTwoName;
            this.userTwoPPUri = userTwoPPUri;
            this.posttitle = postTitle;
        }

    public String getPosttitle() {
        return posttitle;
    }

    public void setPosttitle(String posttitle) {
        this.posttitle = posttitle;
    }

    public String getUserOnePPUri() {
            return userOnePPUri;
        }

        public String getUserTwoPPUri() {
            return userTwoPPUri;
        }

        public String getChatRoomName(){
            return chatRoomName;
        }

        public String getUserOneName(){
            return userOneName;
        }

        public String getUserTwoName(){
            return userTwoName;
        }


        public void setChatRoomName(String chatRoomName) {
            this.chatRoomName = chatRoomName;
        }

        public void setUserOneName(String userOneName) {
            this.userOneName = userOneName;
        }

        public void setUserTwoName(String userTwoName) {
            this.userTwoName = userTwoName;
        }
}
