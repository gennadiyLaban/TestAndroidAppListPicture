package com.example.tagudur.presenters.users.details;

import android.util.Log;

import com.example.tagudur.model.usercase.ChangeUserListener;
import com.example.tagudur.model.usercase.UsersInteractor;
import com.example.tagudur.model.usercase.ErrorMassage;
import com.example.tagudur.model.usercase.User;
import com.example.tagudur.presenters.users.UserVM;

/**
 * Created by Tagudur on 17.02.2018.
 */

public class ImplDetailsUserVM implements IDetailsUserVM, IDetailsScreenActionListener{
    private UsersInteractor coreModel;
    private UserDataConverter converter;

    private int userId;
    private UserVM userVM;

    private IDetailsScreenVMListeners dataChangeListener;

    private boolean isUpdateProcess;

    public ImplDetailsUserVM(UsersInteractor coreModel, final int user_id) {
        this.coreModel = coreModel;
        this.converter = new UserConverter();
        this.userId = user_id;

        isUpdateProcess = true;
        this.coreModel.bindChangeUserListener(new ChangeUserListener() {
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
            public void onFailed(ErrorMassage massage) {
                Log.d("UserListViewModel", massage.getMassage());
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
