package com.example.tagudur.builders.abstractions;

import com.example.tagudur.model.abstractions.ICoreModel;
import com.example.tagudur.viewmodel.abstractions.IUsersScreenVM;

/**
 * Created by Tagudur on 15.02.2018.
 */

public interface IUsersScreenVMFactory {

    public IUsersScreenVM getInstanceUsersScreenVM(ICoreModel coreModel);

}
