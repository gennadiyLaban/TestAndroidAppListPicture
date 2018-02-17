package com.example.tagudur.viewmodel.utiltts;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.tagudur.model.entityes.User;
import com.example.tagudur.viewmodel.abstractions.IUserDataConverter;
import com.example.tagudur.viewmodel.entityes.UserVM;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tagudur on 15.02.2018.
 */

public class UserDataConverter implements IUserDataConverter {

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
        Bitmap picture = BitmapFactory.decodeByteArray(user.getPicture(), 0,
                user.getPicture().length);
        UserVM userVM = new UserVM(user.getId(), user.getFirstName(), user.getLastName(),
                user.getUrlPicture(), picture);
        return userVM;
    }
}
