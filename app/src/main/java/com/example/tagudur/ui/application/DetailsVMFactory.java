package com.example.tagudur.ui.application;

import com.example.tagudur.model.usercase.UsersInteractor;
import com.example.tagudur.presenters.users.details.IDetailsUserVM;

/**
 * Created by Tagudur on 17.02.2018.
 */

public interface DetailsVMFactory {

    public IDetailsUserVM getInstanceDetailsVM(UsersInteractor coreModel, int user_id);

}
