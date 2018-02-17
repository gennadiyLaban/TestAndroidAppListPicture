package com.example.tagudur.testlistpictureapp.aplication;

import android.app.Application;

import com.example.tagudur.builders.DetailsUserVMFactory;
import com.example.tagudur.builders.ModelFactory;
import com.example.tagudur.builders.VModelScreenFactory;
import com.example.tagudur.builders.abstractions.IDetailsUserVMFactory;
import com.example.tagudur.builders.abstractions.IVMRepository;
import com.example.tagudur.builders.abstractions.IModelFactory;
import com.example.tagudur.builders.abstractions.IUsersScreenVMFactory;
import com.example.tagudur.model.abstractions.ICoreModel;
import com.example.tagudur.builders.abstractions.IModelRepository;
import com.example.tagudur.viewmodel.DetailsUserVM;
import com.example.tagudur.viewmodel.abstractions.IDetailsUserVM;
import com.example.tagudur.viewmodel.abstractions.IUsersScreenVM;

/**
 * Created by Tagudur on 15.02.2018.
 */

public class CustomApplication extends Application implements IModelRepository, IVMRepository {

    private ICoreModel coreModel = null;
    private IUsersScreenVM listVModel = null;
    private IDetailsUserVM detailsUserVM = null;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeModel();
        initializeListVModel();
    }




    @Override
    public ICoreModel getModel() {
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
        IModelFactory modelFactory = new ModelFactory();
        coreModel = modelFactory.getInstanceModel();
    }

    private void initializeListVModel() {
        IUsersScreenVMFactory factory = new VModelScreenFactory();
        listVModel = factory.getInstanceUsersScreenVM(getModel());
    }

    private void initializeDetailVM(int userId) {
        IDetailsUserVMFactory factory = new DetailsUserVMFactory();
        detailsUserVM = factory.getInstanceDetailsVM(getModel(), userId);
    }
}
