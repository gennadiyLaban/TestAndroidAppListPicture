package com.example.tagudur.ui.application;

import android.app.Application;

import com.example.tagudur.model.users.UsersInteractor;
import com.example.tagudur.presenters.users.details.IDetailsUserVM;
import com.example.tagudur.presenters.users.list.IUsersScreenVM;

/**
 * Created by Tagudur on 15.02.2018.
 */

public class RepositoryApplication extends Application implements UserInteractRepository, VMRepository {

    private UsersInteractor usersInteractor = null;
    private IUsersScreenVM listVModel = null;
    private IDetailsUserVM detailsUserVM = null;

    private DetailsVMFactory detailsVMFactory = null;
    private UsersScreenVMFactory usersScreenVMFactory = null;

    private final Object lockDetailsVMFactory = new Object();
    private final Object lockUserScreenVMFactory = new Object();
    private boolean isSynch = true;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeUserInteractor();
        initializeUserScreenVM();
    }




    @Override
    public UsersInteractor getUsersInteractor() {
        if(usersInteractor == null) {
            initializeUserInteractor();
        }
        return usersInteractor;
    }

    @Override
    public IUsersScreenVM getUsersScreenVM() {
        if(listVModel == null) {
            initializeUserScreenVM();
        }
        return listVModel;
    }

    @Override
    public IDetailsUserVM getDetailsUserVm(int userId) {
        if(detailsUserVM == null || detailsUserVM.getUserId() != userId) {
            initializeDetailVM(userId);
        }
        return detailsUserVM;
    }

    private void initializeUserInteractor() {
        UserInteractFactory modelFactory = new ImplUserInteractFactory();
        usersInteractor = modelFactory.getInstanceModel();
    }

    private void initializeUserScreenVM() {
        if(usersScreenVMFactory == null) {
            synchronized (lockUserScreenVMFactory) {
                if (usersScreenVMFactory == null) {
                    usersScreenVMFactory = new ImplUsersScreenVMFactory();
                }
            }
        }
        listVModel = usersScreenVMFactory.getInstanceUsersScreenVM(getUsersInteractor());
    }

    private void initializeDetailVM(int userId) {
        if(detailsVMFactory == null) {
            synchronized (lockDetailsVMFactory) {
                if (detailsVMFactory == null) {
                    detailsVMFactory = new ImplDetailsVMFactory();
                }
            }
        }
        detailsUserVM = detailsVMFactory.getInstanceDetailsVM(getUsersInteractor(), userId);
    }
}
