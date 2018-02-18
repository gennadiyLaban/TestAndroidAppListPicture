package com.example.tagudur.ui.application;

import com.example.tagudur.model.usercase.UsersInteractor;
import com.example.tagudur.presenters.users.IUsersScreenVM;

/**
 * Created by Tagudur on 15.02.2018.
 */

public interface UsersScreenVMFactory {

    public IUsersScreenVM getInstanceUsersScreenVM(UsersInteractor coreModel);

}