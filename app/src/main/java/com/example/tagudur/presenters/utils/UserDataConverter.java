package com.example.tagudur.presenters.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.tagudur.model.entityes.User;
import com.example.tagudur.presenters.entitiyes.PresentUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tagudur on 15.02.2018.
 */

public class UserDataConverter implements IUserDataConverter {

    @Override
    public List<PresentUser> convertUserDataList(List<User> users) {
        List<PresentUser> userVMlList = new ArrayList<>(users.size() + 1);
        for (User user: users) {
            PresentUser userVM = convertUserData(user);
            userVMlList.add(userVM);
        }
        return userVMlList;
    }

    @Override
    public PresentUser convertUserData(User user) {
        Bitmap picture = BitmapFactory.decodeByteArray(user.getPicture(), 0,
                user.getPicture().length);
        PresentUser userVM = new PresentUser(user.getId(), user.getFirstName(), user.getLastName(),
                user.getUrlPicture(), picture);
        return userVM;
    }
}
