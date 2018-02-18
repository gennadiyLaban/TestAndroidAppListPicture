package com.example.tagudur.presenters.users.details;

/**
 * Created by Tagudur on 17.02.2018.
 */

public interface IDetailsUserVM {

    public void registrateVMlListeners(IDetailsScreenVMListeners listener);

    public void unregitrateVMListener();

    public IDetailsScreenActionListener getActionListener();

    public int getUserId();

}
