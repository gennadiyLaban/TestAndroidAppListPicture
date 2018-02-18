package com.example.tagudur.presenters.users.list;

import com.example.tagudur.model.users.User;
import com.example.tagudur.presenters.users.UserVM;

import java.util.List;

/**
 * Created by Tagudur on 15.02.2018.
 */

public interface UserDataConverter {

    public List<UserVM> convertUserDataList(List<User> users);

    public UserVM convertUserData(User user);

}
