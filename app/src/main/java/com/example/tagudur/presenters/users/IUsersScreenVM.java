package com.example.tagudur.presenters.users;

import com.example.tagudur.presenters.entitiyes.PresentUser;

import java.util.List;

/**
 * Created by Tagudur on 15.02.2018.
 */

public interface IUsersScreenVM {

    public List<PresentUser> getListUsers();

    public void registrateVMlListeners(IUsersScreenVMlListeners listener);

    public void unregitrateVMListener();

    public void registrateOpenDetailsListener(IListItemsOpenDetailsActivityListener listener);

    public void unregistrateOpenDetailsListener();

    public IUsersScreenActionListener getActionListener();

}
