package com.example.tagudur.model;

import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Tagudur on 14.02.2018.
 */

public class Core implements ICoreModel{
    private IDataRepository repository;
    private volatile boolean isUpdateProgress;

    private List<User> users;
    private List<IChangeDataListener> listeners;

    public Core(IDataRepository repository) {
        this.repository = repository;
        this.users = new ArrayList<>();
        this.listeners = new ArrayList<>();
        this.isUpdateProgress = false;
    }

    @Override
    public List<User> getUserList() {
        return users;
    }

    @Override
    public void registrationChangeDataListener(IChangeDataListener changeDataListener) {
        listeners.add(changeDataListener);
        if(!isUpdateProgress && users.size() > 0) {
            changeDataListener.onDataChanged(users);
        } else {
            if(!isUpdateProgress) {
                updateData();
            }
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
        for (IChangeDataListener listener: listeners)
            listener.onDataChanged(users);
    }

    private void signalFailUpdateData() {
        for (IChangeDataListener listener: listeners)
            listener.onFailed(new IErrorDataMassage() {
                @Override
                public String getMassage() {
                    return "Update data failed. Try again later.";
                }
            });
    }


}
