package com.example.tagudur.ui.application;

import com.example.tagudur.model.users.CacheUsersInteractor;
import com.example.tagudur.model.users.UsersInteractor;
import com.example.tagudur.data.http.HttpUsersRepository;

/**
 * Created by Tagudur on 16.02.2018.
 */

public class ImplUserInteractFactory implements UserInteractFactory {

    @Override
    public UsersInteractor getInstanceModel() {
        CacheUsersInteractor core = new CacheUsersInteractor(new HttpUsersRepository());
        return core;
    }
}
