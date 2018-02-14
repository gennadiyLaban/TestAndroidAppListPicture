package com.example.tagudur.model;

import android.graphics.Bitmap;

import java.util.List;

/**
 * Created by Tagudur on 14.02.2018.
 */

public interface IUsersDAO {

    public List<User> getListUser();

    public User getUser(int id);

}
