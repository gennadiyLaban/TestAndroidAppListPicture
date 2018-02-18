package com.example.tagudur.presenters.details;

import android.util.Log;

import com.example.tagudur.model.usercase.ChangeUserListener;
import com.example.tagudur.model.usercase.UsersInteractor;
import com.example.tagudur.model.usercase.ErrorMassage;
import com.example.tagudur.model.entityes.User;
import com.example.tagudur.presenters.utils.IUserDataConverter;
import com.example.tagudur.presenters.entitiyes.PresentUser;
import com.example.tagudur.presenters.utils.UserDataConverter;

/**
 * Created by Tagudur on 17.02.2018.
 */

public class DetailsUserVM implements IDetailsUserVM, IDetailsScreenActionListener{
    private int idDataListener;
    private UsersInteractor coreModel;
    private IUserDataConverter converter;

    private int userId;
    private PresentUser userVM;

    private IDetailsScreenVMListeners dataChangeListener;

    private boolean isUpdateProcess;

    public DetailsUserVM(UsersInteractor coreModel, final int user_id) {
        this.coreModel = coreModel;
        this.converter = new UserDataConverter();
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
