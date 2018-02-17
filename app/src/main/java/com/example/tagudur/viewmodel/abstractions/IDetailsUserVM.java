package com.example.tagudur.viewmodel.abstractions;

import com.example.tagudur.viewmodel.listeners.IDetailsScreenActionListener;
import com.example.tagudur.viewmodel.listeners.IDetailsScreenVMListeners;
import com.example.tagudur.viewmodel.listeners.IUsersScreenActionListener;

/**
 * Created by Tagudur on 17.02.2018.
 */

public interface IDetailsUserVM {

    public void registrateVMlListeners(IDetailsScreenVMListeners listener);

    public void unregitrateVMListener();

    public IDetailsScreenActionListener getActionListener();

    public int getUserId();

}
