package com.example.tagudur.presenters.users;

import com.example.tagudur.presenters.entitiyes.PresentUser;

import java.util.List;

/**
 * Created by Tagudur on 15.02.2018.
 */

public interface IUsersScreenVMlListeners {

    public void onDataChanged(List<PresentUser> users);

    public void onDataFailed(String message);

}
