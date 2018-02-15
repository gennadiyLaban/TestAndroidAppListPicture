package com.example.tagudur.model;

import java.util.List;

/**
 * Created by Tagudur on 15.02.2018.
 */

public interface ICoreModel {

    public List<User> getUserList();

    public void registrationChangeDataListener(IChangeDataListener changeDataListener);

    public void updateData();

}
