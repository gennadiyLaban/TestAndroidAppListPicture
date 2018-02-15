package com.example.tagudur.model;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.tagudur.model.abstractions.IErrorDataMassage;
import com.example.tagudur.model.utilits.IConstantsModel;
import com.example.tagudur.model.abstractions.ICoreModel;
import com.example.tagudur.model.abstractions.IDataRepository;
import com.example.tagudur.model.callback.IDataCallback;
import com.example.tagudur.model.entityes.User;
import com.example.tagudur.model.listeners.IChangeDataListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Tagudur on 14.02.2018.
 */

public class Core implements ICoreModel {
    private static int baseId = 1;

    private IDataRepository repository;
    private volatile boolean isUpdateProgress;

    private List<User> users;
    private Map<Integer, IChangeDataListener> listeners;

    public Core(IDataRepository repository) {
        this.repository = repository;
        this.users = new ArrayList<>();
        this.listeners = new HashMap<>();
        this.isUpdateProgress = false;
    }

    @Override
    public List<User> getUserList() {
        return users;
    }

    @Override
    public int registrationChangeDataListener(IChangeDataListener changeDataListener) {
        int id = baseId++;
        listeners.put(id, changeDataListener);
        if(!isUpdateProgress && users.size() > 0) {
            changeDataListener.onDataChanged(users);
        } else {
            if(!isUpdateProgress) {
                updateData();
            }
        }
        return id;
    }

    @Override
    public void unregistrationChangeDataListener(int id) {
        IChangeDataListener listener = listeners.remove(id);
        if(listener != null) {
            Log.d("Core", "Unregistration ChangeDataListener successful");
        } else {
            Log.d("Core", "Unregistration ChangeDataListener fail");
        }
    }

    @Override
    public void updateData() {
        if(isUpdateProgress) {
            return;
        }
        ExecutorService service = Executors.newSingleThreadExecutor();

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                isUpdateProgress = false;
                switch (msg.what) {
                    case IConstantsModel.STATUS_OK:
                        signalUpdatedData();
                        break;
                    case IConstantsModel.STATUS_FAIL:
                        signalFailUpdateData();
                        break;
                }
            }
        };

        final IDataCallback callback = new IDataCallback() {
            @Override
            public void onLoadData(List<User> userList) {
                users = userList;
                handler.sendEmptyMessage(IConstantsModel.STATUS_OK);
            }

            @Override
            public void onError() {
                handler.sendEmptyMessage(IConstantsModel.STATUS_FAIL);
            }
        };

        isUpdateProgress = true;
        service.submit(new Runnable() {
            @Override
            public void run() {
                repository.getUserData(callback);
            }
        });
    }

    private void signalUpdatedData() {
        for (IChangeDataListener listener: listeners.values())
            listener.onDataChanged(users);
    }

    private void signalFailUpdateData() {
        for (IChangeDataListener listener: listeners.values())
            listener.onFailed(new IErrorDataMassage() {
                @Override
                public String getMassage() {
                    return "Update data failed. Try again later.";
                }
            });
    }


}
