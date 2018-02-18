package com.example.tagudur.presenters.users;

import android.graphics.Bitmap;

/**
 * Created by Tagudur on 15.02.2018.
 */

public class UserVM {

    private final int id;
    private final String firstName;
    private final String lastName;

    private final String urlPicture;
    private final Bitmap picture;


    public UserVM(int id, String firstName,
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

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUrlPicture() {
        return urlPicture;
    }

    public Bitmap getPicture() {
        return picture;
    }

}
