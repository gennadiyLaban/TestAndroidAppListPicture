package com.example.tagudur.model.usercase;

import java.util.List;

/**
 * Created by Tagudur on 15.02.2018.
 */

public interface ChangeListUserListener {

    public void onDataChanged(List<User> users);

    public void onFailed(ErrorMassage massage);

}
