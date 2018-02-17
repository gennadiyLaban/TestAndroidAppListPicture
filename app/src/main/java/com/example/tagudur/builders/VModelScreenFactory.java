package com.example.tagudur.builders;

import com.example.tagudur.builders.abstractions.IUsersScreenVMFactory;
import com.example.tagudur.model.abstractions.ICoreModel;
import com.example.tagudur.viewmodel.ListUserViewModel;
import com.example.tagudur.viewmodel.abstractions.IUsersScreenVM;

/**
 * Created by Tagudur on 16.02.2018.
 */

public class VModelScreenFactory implements IUsersScreenVMFactory {

    @Override
    public IUsersScreenVM getInstanceUsersScreenVM(ICoreModel coreModel) {
        IUsersScreenVM userListVM = new ListUserViewModel(coreModel);
        return userListVM;
    }
}
