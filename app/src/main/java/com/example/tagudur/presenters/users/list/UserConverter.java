package com.example.tagudur.presenters.users.list;

import com.example.tagudur.model.users.User;
import com.example.tagudur.presenters.users.UserVM;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tagudur on 15.02.2018.
 */

public class UserConverter implements UserDataConverter {

    @Override
    public List<UserVM> convertUserDataList(List<User> users) {
        List<UserVM> userVMlList = new ArrayList<>(users.size() + 1);
        for (User user: users) {
            UserVM userVM = convertUserData(user);
            userVMlList.add(userVM);
        }
        return userVMlList;
    }

    @Override
    public UserVM convertUserData(User user) {
        UserVM userVM = new UserVM(user.getId(), user.getFirstName(), user.getLastName(),
                user.getUrlPicture());
        return userVM;
    }
}
