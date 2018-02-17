package com.example.tagudur.builders.abstractions;

import com.example.tagudur.viewmodel.abstractions.IDetailsUserVM;
import com.example.tagudur.viewmodel.abstractions.IUsersScreenVM;

/**
 * Created by Tagudur on 16.02.2018.
 */

public interface IVMRepository {

    public IUsersScreenVM getListUserVM();

    public IDetailsUserVM getDetailsUserVm(int user_id);

}
