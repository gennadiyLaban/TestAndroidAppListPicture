package com.example.tagudur.builders;

import com.example.tagudur.builders.abstractions.IVMListScreenFactory;
import com.example.tagudur.model.abstractions.ICoreModel;
import com.example.tagudur.viewmodel.ListUserViewModel;
import com.example.tagudur.viewmodel.abstractions.IListUserVM;

/**
 * Created by Tagudur on 16.02.2018.
 */

public class VModelScreenFactory implements IVMListScreenFactory {

    @Override
    public IListUserVM getInstanceListVM(ICoreModel coreModel) {
        IListUserVM userListVM = new ListUserViewModel(coreModel);
        return userListVM;
    }
}
