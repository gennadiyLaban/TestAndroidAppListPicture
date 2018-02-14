package com.example.tagudur.model;

import android.graphics.Bitmap;

/**
 * Created by Tagudur on 14.02.2018.
 */

public class User {

    private int id;
    private String firstName;
    private String lastName;

    private byte[] picture;


    public User(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;

        picture = new byte[1];
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }
}
