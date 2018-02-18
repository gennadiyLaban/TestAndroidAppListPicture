package com.example.tagudur.model.usercase;

import java.util.List;

/**
 * Created by Tagudur on 14.02.2018.
 */

public interface GetUserCallback {

    public void onLoadData(List<User> users);

    public void onError();

}
