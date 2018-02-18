package com.example.tagudur.model.users;

/**
 * Created by Tagudur on 16.02.2018.
 */

public interface ChangeUserListener {

    public int getUserId();

    public void onDataChanged(User user);

    public void onFailed(ErrorMassage massage);

}
