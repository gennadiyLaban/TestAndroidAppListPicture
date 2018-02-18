package com.example.tagudur.presenters.users.list;

import android.util.Log;

import com.example.tagudur.model.users.UsersInteractor;
import com.example.tagudur.model.users.ErrorMassage;
import com.example.tagudur.model.users.User;
import com.example.tagudur.model.users.ChangeListUserListener;
import com.example.tagudur.presenters.users.UserVM;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Tagudur on 15.02.2018.
 */

public class UserListViewModel implements IUsersScreenVM, IUsersScreenActionListener {
    private int idDataListener;
    private UsersInteractor coreModel;
    private UserDataConverter converter;

    private List<UserVM> usersVM;
    private IUsersScreenVMlListeners dataChangeListener;
    private OpenDetailsListener openDetailsListener;

    private boolean isUpdateProcess;
    private ExecutorService executor = Executors.newCachedThreadPool();

    public UserListViewModel(UsersInteractor model) {
        this.coreModel = model;
        this.usersVM = new ArrayList<>();
        this.converter = new UserConverter();

        isUpdateProcess = true;
        this.idDataListener = coreModel.bindChangeUserListener(new ChangeListUserListener() {
            @Override
            public void onDataChanged(final List<User> users) {
                executor.submit(new Runnable() {
                    @Override
                    public void run() {
                        updateData(users);
                    }
                });
            }

            @Override
            public void onFailed(ErrorMassage massage) {
                Log.d("UserListViewModel", massage.getMassage());
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
    public void registrateOpenDetailsListener(OpenDetailsListener listener) {
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

    private void updateData(List<User> users) {
        usersVM = converter.convertUserDataList(users);
        isUpdateProcess = false;
        notifyAllListenersDataChanged();
    }

}
