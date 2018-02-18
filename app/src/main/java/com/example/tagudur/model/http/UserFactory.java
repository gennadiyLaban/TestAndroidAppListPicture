package com.example.tagudur.model.http;

import com.example.tagudur.model.entityes.User;

import java.util.List;

/**
 * Created by Tagudur on 14.02.2018.
 */

public interface UserFactory {

    public List<User> parseJsonUsersData(String json);

}
