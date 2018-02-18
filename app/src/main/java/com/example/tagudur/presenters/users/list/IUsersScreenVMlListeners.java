package com.example.tagudur.presenters.users.list;

import com.example.tagudur.presenters.users.UserVM;

import java.util.List;

/**
 * Created by Tagudur on 15.02.2018.
 */

public interface IUsersScreenVMlListeners {

    public void onDataChanged(List<UserVM> users);

    public void onDataFailed(String message);

}
