package com.example.tagudur.presenters.users;

import android.util.Log;

import com.example.tagudur.model.usercase.UsersInteractor;
import com.example.tagudur.model.usercase.ErrorMassage;
import com.example.tagudur.model.entityes.User;
import com.example.tagudur.model.usercase.ChangeListUserListener;
import com.example.tagudur.presenters.utils.IUserDataConverter;
import com.example.tagudur.presenters.entitiyes.PresentUser;
import com.example.tagudur.presenters.utils.UserDataConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tagudur on 15.02.2018.
 */

public class ListUserViewModel implements IUsersScreenVM, IUsersScreenActionListener {
    private int idDataListener;
    private UsersInteractor coreModel;
    private IUserDataConverter converter;

    private List<PresentUser> usersVM;
    private IUsersScreenVMlListeners dataChangeListener;
    private IListItemsOpenDetailsActivityListener openDetailsListener;

    private boolean isUpdateProcess;

    public ListUserViewModel(UsersInteractor model) {
        this.coreModel = model;
        this.usersVM = new ArrayList<>();
        this.converter = new UserDataConverter();

        isUpdateProcess = true;
        // todo: move out from constructor
        this.idDataListener = coreModel.bindChangeUserListener(new ChangeListUserListener() {
            @Override
            public void onDataChanged(List<User> users) {
                // todo: in other thread
                usersVM = converter.convertUserDataList(users);
                isUpdateProcess = false;
                notifyAllListenersDataChanged();
            }

            @Override
            public void onFailed(ErrorMassage massage) {
                Log.d("ListUserViewModel", massage.getMassage());
                isUpdateProcess = false;
                notifyAllListenersDataFailed(massage.getMassage());
            }
        });
    }

    // IUsersScreenVM
    @Override
    public List<PresentUser> getListUsers() {
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
