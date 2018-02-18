package com.example.tagudur.presenters.details;

import com.example.tagudur.presenters.entitiyes.PresentUser;

/**
 * Created by Tagudur on 17.02.2018.
 */

public interface IDetailsScreenVMListeners {

    public void onDataChanged(PresentUser user);

    public void onDataFailed(String message);

}
