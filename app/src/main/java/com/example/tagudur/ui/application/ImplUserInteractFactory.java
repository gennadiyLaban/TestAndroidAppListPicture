package com.example.tagudur.ui.application;

import com.example.tagudur.model.usercase.CacheUsersInteractor;
import com.example.tagudur.model.usercase.UsersInteractor;
import com.example.tagudur.model.http.HttpUsersRepository;

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
