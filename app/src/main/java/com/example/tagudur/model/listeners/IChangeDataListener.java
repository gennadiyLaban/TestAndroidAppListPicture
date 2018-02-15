package com.example.tagudur.model.listeners;

import com.example.tagudur.model.abstractions.IErrorDataMassage;
import com.example.tagudur.model.entityes.User;

import java.util.List;

/**
 * Created by Tagudur on 15.02.2018.
 */

public interface IChangeDataListener {

    public void onDataChanged(List<User> users);

    public void onFailed(IErrorDataMassage massage);

}
