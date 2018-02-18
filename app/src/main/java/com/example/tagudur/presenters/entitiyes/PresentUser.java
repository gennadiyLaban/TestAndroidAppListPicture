package com.example.tagudur.presenters.entitiyes;

import android.graphics.Bitmap;

/**
 * Created by Tagudur on 15.02.2018.
 */

public class PresentUser {

    private int id;
    private String firstName;
    private String lastName;

    private String urlPicture;
    private Bitmap picture;


    public PresentUser(int id, String firstName,
                       String lastName, String urlPicture, Bitmap picture) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;

        this.urlPicture = urlPicture;
        this.picture = picture;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUrlPicture() {
        return urlPicture;
    }

    public void setUrlPicture(String urlPicture) {
        this.urlPicture = urlPicture;
    }

    public Bitmap getPicture() {
        return picture;
    }

    public void setPicture(Bitmap picture) {
        this.picture = picture;
    }
}
