package com.example.tagudur.data.http;

import com.example.tagudur.model.users.User;

import java.util.List;

/**
 * Created by Tagudur on 14.02.2018.
 */

public interface UserMapper {

    public List<User> parseJsonUsersData(String json);

}
