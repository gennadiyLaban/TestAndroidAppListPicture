package com.example.tagudur.model.abstractions;

import com.example.tagudur.model.entityes.User;

import java.util.List;

/**
 * Created by Tagudur on 16.02.2018.
 */

public interface IChangeUserListener {

    public int getUserId();

    public void onDataChanged(User user);

    public void onFailed(IErrorDataMassage massage);

}
