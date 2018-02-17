package com.example.tagudur.model;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.tagudur.model.abstractions.IChangeUserListener;
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

    // todo: stored User in HashMap: Key-user_id: Value-user
    private List<User> users;
    private Map<Integer, IChangeDataListener> dataListeners;
    private Map<Integer, IChangeUserListener> userListeners;

    public Core(IDataRepository repository) {
        this.repository = repository;
        this.users = new ArrayList<>();
        this.dataListeners = new HashMap<>();
        this.userListeners = new HashMap<>();
        this.isUpdateProgress = false;
    }

    @Override
    public List<User> getUserList() {
        return users;
    }

    @Override
    public int bindChangeUserListener(IChangeDataListener changeDataListener) {
        int id = baseId++;
        dataListeners.put(id, changeDataListener);
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
    public void unbindChangeDataListener(int id) {
        IChangeDataListener listener = dataListeners.remove(id);
        if(listener != null) {
            Log.d("Core", "Unregistration ChangeDataListener successful");
        } else {
            Log.d("Core", "Unregistration ChangeDataListener fail");
        }
    }

    @Override
    public int bindChangeUserListener(IChangeUserListener changeUserListener) {
        int id = baseId++;
        userListeners.put(id, changeUserListener);
        if(!isUpdateProgress && users.size() > 0) {
            signalOnUpdatedData(changeUserListener);
        } else {
            if(!isUpdateProgress) {
                updateData();
            }
        }
        return id;
    }

    @Override
    public void unbindChangeUserListener(int id) {

    }

    @Override
    public void updateData() {
        if(isUpdateProgress) {
            return;
        }
        ExecutorService service = Executors.newSingleThreadExecutor();

        Handler handler = getSignalHandler();
        final IDataCallback callback = getDataCallback(handler);

        isUpdateProgress = true;
        service.submit(new Runnable() {
            @Override
            public void run() {
                repository.getUserData(callback);
            }
        });
    }

    private void signalUpdatedData() {
        for (IChangeDataListener listener: dataListeners.values())
            listener.onDataChanged(users);
        for (IChangeUserListener listener: userListeners.values()) {
            signalOnUpdatedData(listener);
        }
    }

    private void signalFailUpdateData() {
        for (IChangeDataListener listener: dataListeners.values())
            listener.onFailed(new IErrorDataMassage() {
                @Override
                public String getMassage() {
                    return "Update data failed. Try again later.";
                }
            });
        for (IChangeUserListener listener: userListeners.values())
            listener.onFailed(new IErrorDataMassage() {
                @Override
                public String getMassage() {
                    return "Update data failed. Try again later.";
                }
            });
    }

    private void signalOnUpdatedData(IChangeUserListener userListener) {
        int user_id = userListener.getUserId();
        User user = getUserById(user_id);
        if(user != null) {
            userListener.onDataChanged(user);
        } else {
            userListener.onFailed(new IErrorDataMassage() {
                @Override
                public String getMassage() {
                    return "Sorry. User with this name not found.";
                }
            });
        }
    }

    private User getUserById(int user_id) {
        User userById = null;
        for (User user: users)
            if (user_id == user.getId()) {
                userById = user;
                break;
            }
        return userById;
    }

    private Handler getSignalHandler() {
        return new Handler() {
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
    }

    private IDataCallback getDataCallback(final Handler signalHandler) {
        return new IDataCallback() {
            @Override
            public void onLoadData(List<User> userList) {
                users = userList;
                signalHandler.sendEmptyMessage(IConstantsModel.STATUS_OK);
            }

            @Override
            public void onError() {
                signalHandler.sendEmptyMessage(IConstantsModel.STATUS_FAIL);
            }
        };
    }

}
