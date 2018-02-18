package com.example.tagudur.ui.application;

import android.app.Application;

import com.example.tagudur.model.users.UsersInteractor;
import com.example.tagudur.presenters.users.details.IDetailsUserVM;
import com.example.tagudur.presenters.users.list.IUsersScreenVM;

/**
 * Created by Tagudur on 15.02.2018.
 */

public class RepositoryApplication extends Application implements UserInteractRepository, VMRepository {

    private UsersInteractor coreModel = null;
    private IUsersScreenVM listVModel = null;
    private IDetailsUserVM detailsUserVM = null;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeModel();
        initializeListVModel();
    }




    @Override
    public UsersInteractor getModel() {
        if(coreModel == null) {
            initializeModel();
        }
        return coreModel;
    }

    @Override
    public IUsersScreenVM getListUserVM() {
        if(listVModel == null) {
            initializeListVModel();
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

    private void initializeModel() {
        UserInteractFactory modelFactory = new ImplUserInteractFactory();
        coreModel = modelFactory.getInstanceModel();
    }

    private void initializeListVModel() {
        UsersScreenVMFactory factory = new ImplUsersScreenVMFactory();
        listVModel = factory.getInstanceUsersScreenVM(getModel());
    }

    private void initializeDetailVM(int userId) {
        DetailsVMFactory factory = new ImplDetailsVMFactory();
        detailsUserVM = factory.getInstanceDetailsVM(getModel(), userId);
    }
}
