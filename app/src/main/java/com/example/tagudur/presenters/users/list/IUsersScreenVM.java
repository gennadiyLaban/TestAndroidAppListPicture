package com.example.tagudur.presenters.users.list;

import com.example.tagudur.presenters.users.UserVM;

import java.util.List;

/**
 * Created by Tagudur on 15.02.2018.
 */

public interface IUsersScreenVM {

    public List<UserVM> getListUsers();

    public void registrateVMlListeners(IUsersScreenVMlListeners listener);

    public void unregitrateVMListener();

    public void registrateOpenDetailsListener(OpenDetailsListener listener);

    public void unregistrateOpenDetailsListener();

    public IUsersScreenActionListener getActionListener();

    public void update();

}
