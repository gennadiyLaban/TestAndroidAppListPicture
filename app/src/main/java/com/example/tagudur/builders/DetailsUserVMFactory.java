package com.example.tagudur.builders;

import com.example.tagudur.builders.abstractions.IDetailsUserVMFactory;
import com.example.tagudur.model.Core;
import com.example.tagudur.model.abstractions.ICoreModel;
import com.example.tagudur.model.web.LoadHandler;
import com.example.tagudur.viewmodel.DetailsUserVM;
import com.example.tagudur.viewmodel.ListUserViewModel;
import com.example.tagudur.viewmodel.abstractions.IDetailsUserVM;
import com.example.tagudur.viewmodel.abstractions.IUsersScreenVM;

/**
 * Created by Tagudur on 17.02.2018.
 */

public class DetailsUserVMFactory implements IDetailsUserVMFactory {

    @Override
    public IDetailsUserVM getInstanceDetailsVM(ICoreModel coreModel, int user_id) {
        IDetailsUserVM detailsUserVM = new DetailsUserVM(coreModel, user_id);
        return detailsUserVM;
    }
}
