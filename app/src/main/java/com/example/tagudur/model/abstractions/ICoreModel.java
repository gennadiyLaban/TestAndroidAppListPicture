package com.example.tagudur.model.abstractions;

import com.example.tagudur.model.entityes.User;
import com.example.tagudur.model.listeners.IChangeDataListener;

import java.util.List;

/**
 * Created by Tagudur on 15.02.2018.
 */

public interface ICoreModel {

    public List<User> getUserList();

    public void registrationChangeDataListener(IChangeDataListener changeDataListener);

    public void updateData();

}
