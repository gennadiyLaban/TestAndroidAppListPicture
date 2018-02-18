package com.example.tagudur.presenters.users.details;

import com.example.tagudur.presenters.users.UserVM;

/**
 * Created by Tagudur on 17.02.2018.
 */

public interface IDetailsScreenVMListeners {

    public void onDataChanged(UserVM user);

    public void onDataFailed(String message);

}
