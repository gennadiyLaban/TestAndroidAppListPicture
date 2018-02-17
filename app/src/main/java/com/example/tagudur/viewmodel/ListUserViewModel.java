package com.example.tagudur.viewmodel;

import android.util.Log;

import com.example.tagudur.model.abstractions.ICoreModel;
import com.example.tagudur.model.abstractions.IErrorDataMassage;
import com.example.tagudur.model.entityes.User;
import com.example.tagudur.model.listeners.IChangeDataListener;
import com.example.tagudur.viewmodel.abstractions.IUsersScreenVM;
import com.example.tagudur.viewmodel.abstractions.IUserDataConverter;
import com.example.tagudur.viewmodel.entityes.UserVM;
import com.example.tagudur.viewmodel.listeners.IUsersScreenActionListener;
import com.example.tagudur.viewmodel.listeners.IListItemsOpenDetailsActivityListener;
import com.example.tagudur.viewmodel.listeners.IUsersScreenVMlListeners;
import com.example.tagudur.viewmodel.utiltts.UserDataConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tagudur on 15.02.2018.
 */

public class ListUserViewModel implements IUsersScreenVM, IUsersScreenActionListener {
    private int idDataListener;
    private ICoreModel coreModel;
    private IUserDataConverter converter;

    private List<UserVM> usersVM;
    private IUsersScreenVMlListeners dataChangeListener;
    private IListItemsOpenDetailsActivityListener openDetailsListener;

    private boolean isUpdateProcess;

    public ListUserViewModel(ICoreModel model) {
        this.coreModel = model;
        this.usersVM = new ArrayList<>();
        this.converter = new UserDataConverter();

        isUpdateProcess = true;
        // todo: move out from constructor
        this.idDataListener = coreModel.bindChangeUserListener(new IChangeDataListener() {
            @Override
            public void onDataChanged(List<User> users) {
                // todo: in other thread
                usersVM = converter.convertUserDataList(users);
                isUpdateProcess = false;
                notifyAllListenersDataChanged();
            }

            @Override
            public void onFailed(IErrorDataMassage massage) {
                Log.d("ListUserViewModel", massage.getMassage());
                isUpdateProcess = false;
                notifyAllListenersDataFailed(massage.getMassage());
            }
        });
    }

    // IUsersScreenVM
    @Override
    public List<UserVM> getListUsers() {
        return usersVM;
    }

    @Override
    public void registrateVMlListeners(IUsersScreenVMlListeners listener) {
        this.dataChangeListener = listener;
        if(!isUpdateProcess) {
            listener.onDataChanged(usersVM);
        }
    }

    @Override
    public void unregitrateVMListener() {
        this.dataChangeListener = null;
    }

    @Override
    public IUsersScreenActionListener getActionListener() {
        return this;
    }

    @Override
    public void registrateOpenDetailsListener(IListItemsOpenDetailsActivityListener listener) {
        this.openDetailsListener = listener;
    }

    @Override
    public void unregistrateOpenDetailsListener() {
        this.openDetailsListener = null;
    }

    // IUsersScreenActionListener
    @Override
    public void onItemClick(int id) {
        notifyAllListenersOpenDetails(id);
    }

    @Override
    public void onStop() {
        unregistrateOpenDetailsListener();
        unregitrateVMListener();
    }



    private void notifyAllListenersDataChanged(){
        if(dataChangeListener != null) {
            dataChangeListener.onDataChanged(usersVM);
        }
    }

    private void notifyAllListenersDataFailed(String message){
        if(dataChangeListener != null) {
            dataChangeListener.onDataFailed(message);
        }
    }

    private void notifyAllListenersOpenDetails(int id) {
        if(this.openDetailsListener != null)
            this.openDetailsListener.onOpenDetailActivity(id);
    }

}
