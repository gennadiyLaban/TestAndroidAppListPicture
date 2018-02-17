package com.example.tagudur.viewmodel;

import android.util.Log;

import com.example.tagudur.model.abstractions.IChangeUserListener;
import com.example.tagudur.model.abstractions.ICoreModel;
import com.example.tagudur.model.abstractions.IErrorDataMassage;
import com.example.tagudur.model.entityes.User;
import com.example.tagudur.viewmodel.abstractions.IDetailsUserVM;
import com.example.tagudur.viewmodel.abstractions.IUserDataConverter;
import com.example.tagudur.viewmodel.entityes.UserVM;
import com.example.tagudur.viewmodel.listeners.IDetailsScreenActionListener;
import com.example.tagudur.viewmodel.listeners.IDetailsScreenVMListeners;
import com.example.tagudur.viewmodel.listeners.IUsersScreenActionListener;
import com.example.tagudur.viewmodel.utiltts.UserDataConverter;

/**
 * Created by Tagudur on 17.02.2018.
 */

public class DetailsUserVM implements IDetailsUserVM, IDetailsScreenActionListener{
    private int idDataListener;
    private ICoreModel coreModel;
    private IUserDataConverter converter;

    private int userId;
    private UserVM userVM;

    private IDetailsScreenVMListeners dataChangeListener;

    private boolean isUpdateProcess;

    public DetailsUserVM(ICoreModel coreModel, final int user_id) {
        this.coreModel = coreModel;
        this.converter = new UserDataConverter();
        this.userId = user_id;

        isUpdateProcess = true;
        this.coreModel.bindChangeUserListener(new IChangeUserListener() {
            @Override
            public int getUserId() {
                return userId;
            }

            @Override
            public void onDataChanged(User user) {
                userVM = converter.convertUserData(user);
                isUpdateProcess = false;
                notifyDataChanged();
            }

            @Override
            public void onFailed(IErrorDataMassage massage) {
                Log.d("ListUserViewModel", massage.getMassage());
                isUpdateProcess = false;
                notifyDataFailed(massage.getMassage());
            }
        });

    }

    // IDetailsUserVM


    @Override
    public int getUserId() {
        return userId;
    }

    @Override
    public void registrateVMlListeners(IDetailsScreenVMListeners listener) {
        this.dataChangeListener = listener;
        if(!isUpdateProcess) {
            listener.onDataChanged(userVM);
        }
    }

    @Override
    public void unregitrateVMListener() {
        this.dataChangeListener = null;
    }

    @Override
    public IDetailsScreenActionListener getActionListener() {
        return this;
    }

    // IDetailsScreenActionListener


    @Override
    public void onDestroy() {
        unregitrateVMListener();
    }

    private void notifyDataChanged(){
        if(dataChangeListener != null) {
            dataChangeListener.onDataChanged(userVM);
        }
    }

    private void notifyDataFailed(String message){
        if(dataChangeListener != null) {
            dataChangeListener.onDataFailed(message);
        }
    }
}
