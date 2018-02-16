package com.example.tagudur.builders.abstractions;

import com.example.tagudur.model.abstractions.ICoreModel;
import com.example.tagudur.viewmodel.abstractions.IListUserVM;

/**
 * Created by Tagudur on 15.02.2018.
 */

public interface IVMListScreenFactory {

    public IListUserVM getInstanceListVM(ICoreModel coreModel);

}
