package com.example.tagudur.presenters.users.details;

import android.util.Log;

import com.example.tagudur.model.users.ChangeUserListener;
import com.example.tagudur.model.users.UsersInteractor;
import com.example.tagudur.model.users.ErrorMassage;
import com.example.tagudur.model.users.User;
import com.example.tagudur.presenters.users.UserVM;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Tagudur on 17.02.2018.
 */

public class ImplDetailsUserVM implements IDetailsUserVM, IDetailsScreenActionListener{
    private UsersInteractor usersInteractor;
    private UserDataConverter converter;

    private int userId;
    private UserVM userVM;

    private IDetailsScreenVMListeners dataChangeListener;

    private boolean isUpdateProcess;
    private ExecutorService executor = Executors.newCachedThreadPool();

    public ImplDetailsUserVM(UsersInteractor usersInteractor, final int user_id) {
        this.usersInteractor = usersInteractor;
        this.converter = new UserConverter();
        this.userId = user_id;

        isUpdateProcess = true;
        this.usersInteractor.bindChangeUserListener(new ChangeUserListener() {
            @Override
            public int getUserId() {
                return userId;
            }

            @Override
            public void onDataChanged(final User user) {
                executor.submit(new Runnable() {
                    @Override
                    public void run() {
                        updateData(user);
                    }
                });
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

    private void updateData(User user) {
        userVM = converter.convertUserData(user);
        isUpdateProcess = false;
        notifyDataChanged();
    }
}
