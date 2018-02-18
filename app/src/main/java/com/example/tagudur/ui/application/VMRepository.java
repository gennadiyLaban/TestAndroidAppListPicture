package com.example.tagudur.ui.application;

import com.example.tagudur.presenters.details.IDetailsUserVM;
import com.example.tagudur.presenters.users.IUsersScreenVM;

/**
 * Created by Tagudur on 16.02.2018.
 */

public interface VMRepository {

    public IUsersScreenVM getListUserVM();

    public IDetailsUserVM getDetailsUserVm(int user_id);

}
