package com.example.tagudur.viewmodel.abstractions;

import com.example.tagudur.viewmodel.entityes.UserVM;
import com.example.tagudur.viewmodel.listeners.IUsersScreenActionListener;
import com.example.tagudur.viewmodel.listeners.IListItemsOpenDetailsActivityListener;
import com.example.tagudur.viewmodel.listeners.IUsersScreenVMlListeners;

import java.util.List;

/**
 * Created by Tagudur on 15.02.2018.
 */

public interface IUsersScreenVM {

    public List<UserVM> getListUsers();

    public void registrateVMlListeners(IUsersScreenVMlListeners listener);

    public void unregitrateVMListener();

    public void registrateOpenDetailsListener(IListItemsOpenDetailsActivityListener listener);

    public void unregistrateOpenDetailsListener();

    public IUsersScreenActionListener getActionListener();

}
