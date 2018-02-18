package com.example.tagudur.presenters.utils;

import com.example.tagudur.model.entityes.User;
import com.example.tagudur.presenters.entitiyes.PresentUser;

import java.util.List;

/**
 * Created by Tagudur on 15.02.2018.
 */

public interface IUserDataConverter {

    public List<PresentUser> convertUserDataList(List<User> users);

    public PresentUser convertUserData(User user);

}
