package com.example.tagudur.model.web;

import com.example.tagudur.model.User;

import java.util.List;

/**
 * Created by Tagudur on 14.02.2018.
 */

public interface IUserFactory {

    public List<User> parseJsonUsersData(String json);

}
