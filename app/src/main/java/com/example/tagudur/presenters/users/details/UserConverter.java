package com.example.tagudur.presenters.users.details;

import com.example.tagudur.model.users.User;
import com.example.tagudur.presenters.users.UserVM;

/**
 * Created by Tagudur on 15.02.2018.
 */

public class UserConverter implements UserDataConverter {

    @Override
    public UserVM convertUserData(User user) {
        UserVM userVM = new UserVM(user.getId(), user.getFirstName(), user.getLastName(),
                user.getUrlPicture());
        return userVM;
    }
}
