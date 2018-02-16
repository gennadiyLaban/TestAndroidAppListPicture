package com.example.tagudur.testlistpictureapp.aplication;

import android.app.Application;

import com.example.tagudur.builders.ModelFactory;
import com.example.tagudur.builders.VModelScreenFactory;
import com.example.tagudur.builders.abstractions.IListVMRepository;
import com.example.tagudur.builders.abstractions.IModelFactory;
import com.example.tagudur.builders.abstractions.IVMListScreenFactory;
import com.example.tagudur.model.abstractions.ICoreModel;
import com.example.tagudur.builders.abstractions.IModelRepository;
import com.example.tagudur.viewmodel.abstractions.IListUserVM;

/**
 * Created by Tagudur on 15.02.2018.
 */

public class CustomApplication extends Application implements IModelRepository, IListVMRepository {

    private ICoreModel coreModel = null;
    private IListUserVM listVModel = null;

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
    public IListUserVM getListUserVM() {
        if(listVModel == null) {
            initializeListVModel();
        }
        return listVModel;
    }

    private void initializeModel() {
        IModelFactory modelFactory = new ModelFactory();
        coreModel = modelFactory.getInstanceModel();
    }

    private void initializeListVModel() {
        IVMListScreenFactory factory = new VModelScreenFactory();
        listVModel = factory.getInstanceListVM(getModel());
    }
}
