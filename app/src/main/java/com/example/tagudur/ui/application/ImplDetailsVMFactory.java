package com.example.tagudur.ui.application;

import com.example.tagudur.model.users.UsersInteractor;
import com.example.tagudur.presenters.users.details.ImplDetailsUserVM;
import com.example.tagudur.presenters.users.details.IDetailsUserVM;

/**
 * Created by Tagudur on 17.02.2018.
 */

public class ImplDetailsVMFactory implements DetailsVMFactory {

    @Override
    public IDetailsUserVM getInstanceDetailsVM(UsersInteractor usersInteractor, int user_id) {
        IDetailsUserVM detailsUserVM = new ImplDetailsUserVM(usersInteractor, user_id);
        return detailsUserVM;
    }
}
