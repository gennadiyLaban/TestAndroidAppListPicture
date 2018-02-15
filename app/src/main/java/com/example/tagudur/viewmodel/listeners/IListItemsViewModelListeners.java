package com.example.tagudur.viewmodel.listeners;

import com.example.tagudur.viewmodel.entityes.UserViewModel;

import java.util.List;

/**
 * Created by Tagudur on 15.02.2018.
 */

public interface IListItemsViewModelListeners {

    public void onDataChanged(List<UserViewModel> users);

    public void onDataFailed(String message);

}
