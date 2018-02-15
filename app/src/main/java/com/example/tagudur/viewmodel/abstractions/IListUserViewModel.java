package com.example.tagudur.viewmodel.abstractions;

import com.example.tagudur.viewmodel.entityes.UserViewModel;
import com.example.tagudur.viewmodel.listeners.IListItemsActionListener;
import com.example.tagudur.viewmodel.listeners.IListItemsOpenDetailsActivityListener;
import com.example.tagudur.viewmodel.listeners.IListItemsViewModelListeners;

import java.util.List;

/**
 * Created by Tagudur on 15.02.2018.
 */

public interface IListUserViewModel {

    public List<UserViewModel> getListUsers();

    public void registrateListVMListeners(IListItemsViewModelListeners listener);

    public void unregitrateVMListener();

    public void registrateOpenDetailsActivityListener(IListItemsOpenDetailsActivityListener listener);

    public void unregistrateOpenDetailsActivityListener();

    public IListItemsActionListener getActionListener();

}
