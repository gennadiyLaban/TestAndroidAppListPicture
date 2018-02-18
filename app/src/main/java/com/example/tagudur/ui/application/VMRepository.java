package com.example.tagudur.ui.application;

import com.example.tagudur.presenters.users.details.IDetailsUserVM;
import com.example.tagudur.presenters.users.list.IUsersScreenVM;

/**
 * Created by Tagudur on 16.02.2018.
 */

public interface VMRepository {

    public IUsersScreenVM getUsersScreenVM();

    public IDetailsUserVM getDetailsUserVm(int user_id);

}
