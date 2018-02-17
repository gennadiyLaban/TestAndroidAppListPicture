package com.example.tagudur.viewmodel.abstractions;

import com.example.tagudur.model.entityes.User;
import com.example.tagudur.viewmodel.entityes.UserVM;

import java.util.List;

/**
 * Created by Tagudur on 15.02.2018.
 */

public interface IUserDataConverter {

    public List<UserVM> convertUserDataList(List<User> users);

    public UserVM convertUserData(User user);

}
