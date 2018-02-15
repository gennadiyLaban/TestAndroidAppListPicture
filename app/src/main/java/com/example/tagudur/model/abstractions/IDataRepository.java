package com.example.tagudur.model.abstractions;

import com.example.tagudur.model.callback.IDataCallback;

/**
 * Created by Tagudur on 14.02.2018.
 */

public interface IDataRepository {

    public void getUserData(IDataCallback listener);

}
