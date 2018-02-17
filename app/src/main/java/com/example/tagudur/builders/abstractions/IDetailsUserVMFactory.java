package com.example.tagudur.builders.abstractions;

import com.example.tagudur.model.abstractions.ICoreModel;
import com.example.tagudur.viewmodel.abstractions.IDetailsUserVM;
import com.example.tagudur.viewmodel.abstractions.IUsersScreenVM;

/**
 * Created by Tagudur on 17.02.2018.
 */

public interface IDetailsUserVMFactory {

    public IDetailsUserVM getInstanceDetailsVM(ICoreModel coreModel, int user_id);

}
