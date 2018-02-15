package com.example.tagudur.model.callback;

import com.example.tagudur.model.entityes.User;

import java.util.List;

/**
 * Created by Tagudur on 14.02.2018.
 */

public interface IDataCallback {

    public void onLoadData(List<User> users);

    public void onError();

}
