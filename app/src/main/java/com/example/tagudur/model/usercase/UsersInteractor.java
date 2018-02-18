package com.example.tagudur.model.usercase;

import java.util.List;

/**
 * Created by Tagudur on 15.02.2018.
 */

public interface UsersInteractor {

    public List<User> getUserList();

    public int bindChangeUserListener(ChangeListUserListener changeDataListener);

    public void unbindChangeDataListener(int id);


    public int bindChangeUserListener(ChangeUserListener changeDataListener);

    public void unbindChangeUserListener(int id);

    public void updateData();

}
