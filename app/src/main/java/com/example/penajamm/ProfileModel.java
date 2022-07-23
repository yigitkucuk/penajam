package com.example.penajamm;

public class ProfileModel {
    String imageUri;

    public ProfileModel() { }
    public ProfileModel(String imageUri) {

        this.imageUri = imageUri;

    }



    public String getImageUri() {
        return this.imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}

