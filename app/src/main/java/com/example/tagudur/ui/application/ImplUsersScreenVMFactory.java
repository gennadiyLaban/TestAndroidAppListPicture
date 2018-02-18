package com.example.tagudur.ui.application;

import com.example.tagudur.model.usercase.UsersInteractor;
import com.example.tagudur.presenters.users.ListUserViewModel;
import com.example.tagudur.presenters.users.IUsersScreenVM;

/**
 * Created by Tagudur on 16.02.2018.
 */

public class ImplUsersScreenVMFactory implements UsersScreenVMFactory {

    @Override
    public IUsersScreenVM getInstanceUsersScreenVM(UsersInteractor coreModel) {
        IUsersScreenVM userListVM = new ListUserViewModel(coreModel);
        return userListVM;
    }
}
