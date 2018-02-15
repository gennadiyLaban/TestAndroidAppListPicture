package com.example.tagudur.viewmodel;

import android.util.Log;

import com.example.tagudur.model.abstractions.ICoreModel;
import com.example.tagudur.model.abstractions.IErrorDataMassage;
import com.example.tagudur.model.entityes.User;
import com.example.tagudur.model.listeners.IChangeDataListener;
import com.example.tagudur.viewmodel.abstractions.IListUserViewModel;
import com.example.tagudur.viewmodel.abstractions.IUserDataConverter;
import com.example.tagudur.viewmodel.entityes.UserViewModel;
import com.example.tagudur.viewmodel.listeners.IListItemsActionListener;
import com.example.tagudur.viewmodel.listeners.IListItemsOpenDetailsActivityListener;
import com.example.tagudur.viewmodel.listeners.IListItemsViewModelListeners;
import com.example.tagudur.viewmodel.utiltts.UserDataConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tagudur on 15.02.2018.
 */

public class ListUserViewModel implements IListUserViewModel, IListItemsActionListener {
    private int idDataListener;
    private ICoreModel coreModel;
    private IUserDataConverter converter;

    private List<UserViewModel> usersVM;
    private IListItemsViewModelListeners dataChangeListener;
    private IListItemsOpenDetailsActivityListener openDetailsListener;

    private boolean isFlipingScreen;
    private boolean isUpdateProcess;

    public ListUserViewModel(ICoreModel model) {
        this.coreModel = model;
        this.usersVM = new ArrayList<>();
        this.converter = new UserDataConverter();

        isUpdateProcess = true;
        // todo: move out from constructor
        this.idDataListener = coreModel.registrationChangeDataListener(new IChangeDataListener() {
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

    // IListUserViewModel
    @Override
    public List<UserViewModel> getListUsers() {
        return usersVM;
    }

    @Override
    public void registrateListVMListeners(IListItemsViewModelListeners listener) {
        this.isFlipingScreen = false;
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
    public IListItemsActionListener getActionListener() {
        return this;
    }

    @Override
    public void registrateOpenDetailsActivityListener(IListItemsOpenDetailsActivityListener listener) {
        this.isFlipingScreen = false;
        this.openDetailsListener = listener;
    }

    @Override
    public void unregistrateOpenDetailsActivityListener() {
        this.openDetailsListener = null;
    }

    // IListItemsActionListener
    @Override
    public void onItemClick(int id) {
        notifyAllListenersOpenDetails(id);
    }

    @Override
    public void onFlipScreen() {
        isFlipingScreen = true;
    }

    @Override
    public void onDestroy() {
        unregistrateOpenDetailsActivityListener();
        unregitrateVMListener();

        if(!isFlipingScreen) {
            // todo: remove link from repository
            coreModel.unregistrationChangeDataListener(idDataListener);
        }
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
