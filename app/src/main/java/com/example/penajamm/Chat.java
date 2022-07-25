package com.example.penajamm;

public class Chat {

        private String chatRoomName;
        private String userOneName;
        private String userTwoName;

        public Chat(){

        }

        public Chat(String chatRoomName, String userOneName, String userTwoName){
            this.chatRoomName = chatRoomName;
            this.userOneName = userOneName;
            this.userTwoName = userTwoName;
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

}
