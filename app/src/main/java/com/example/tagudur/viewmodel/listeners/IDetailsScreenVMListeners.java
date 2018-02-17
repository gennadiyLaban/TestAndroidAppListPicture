package com.example.tagudur.viewmodel.listeners;

import com.example.tagudur.viewmodel.entityes.UserVM;

import java.util.List;

/**
 * Created by Tagudur on 17.02.2018.
 */

public interface IDetailsScreenVMListeners {

    public void onDataChanged(UserVM user);

    public void onDataFailed(String message);

}
