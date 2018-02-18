package com.example.tagudur.ui.application;

import com.example.tagudur.model.usercase.UsersInteractor;
import com.example.tagudur.presenters.users.list.UserListViewModel;
import com.example.tagudur.presenters.users.list.IUsersScreenVM;

/**
 * Created by Tagudur on 16.02.2018.
 */

public class ImplUsersScreenVMFactory implements UsersScreenVMFactory {

    @Override
    public IUsersScreenVM getInstanceUsersScreenVM(UsersInteractor coreModel) {
        IUsersScreenVM userListVM = new UserListViewModel(coreModel);
        return userListVM;
    }
}
