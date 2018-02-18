package com.example.tagudur.ui.application;

import com.example.tagudur.model.usercase.UsersInteractor;
import com.example.tagudur.presenters.details.DetailsUserVM;
import com.example.tagudur.presenters.details.IDetailsUserVM;

/**
 * Created by Tagudur on 17.02.2018.
 */

public class ImplDetailsVMFactory implements DetailsVMFactory {

    @Override
    public IDetailsUserVM getInstanceDetailsVM(UsersInteractor coreModel, int user_id) {
        IDetailsUserVM detailsUserVM = new DetailsUserVM(coreModel, user_id);
        return detailsUserVM;
    }
}
