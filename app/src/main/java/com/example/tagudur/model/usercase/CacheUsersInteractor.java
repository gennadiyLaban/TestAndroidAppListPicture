package com.example.tagudur.model.usercase;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Tagudur on 14.02.2018.
 */

public class CacheUsersInteractor implements UsersInteractor {
    private static int baseId = 1;

    private UserRepository repository;
    private volatile boolean isUpdateProgress;

    // todo: stored User in HashMap: Key-user_id: Value-user
    private List<User> users;
    private Map<Integer, ChangeListUserListener> dataListeners;
    private Map<Integer, ChangeUserListener> userListeners;

    public CacheUsersInteractor(UserRepository repository) {
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
    public int bindChangeUserListener(ChangeListUserListener changeDataListener) {
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
        ChangeListUserListener listener = dataListeners.remove(id);
        if(listener != null) {
            Log.d("CacheUsersInteractor", "Unregistration ChangeDataListener successful");
        } else {
            Log.d("CacheUsersInteractor", "Unregistration ChangeDataListener fail");
        }
    }

    @Override
    public int bindChangeUserListener(ChangeUserListener changeUserListener) {
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
        final GetUserCallback callback = getDataCallback(handler);

        isUpdateProgress = true;
        service.submit(new Runnable() {
            @Override
            public void run() {
                repository.getUserData(callback);
            }
        });
    }

    private void signalUpdatedData() {
        for (ChangeListUserListener listener: dataListeners.values())
            listener.onDataChanged(users);
        for (ChangeUserListener listener: userListeners.values()) {
            signalOnUpdatedData(listener);
        }
    }

    private void signalFailUpdateData() {
        for (ChangeListUserListener listener: dataListeners.values())
            listener.onFailed(new ErrorMassage() {
                @Override
                public String getMassage() {
                    return "Update data failed. Try again later.";
                }
            });
        for (ChangeUserListener listener: userListeners.values())
            listener.onFailed(new ErrorMassage() {
                @Override
                public String getMassage() {
                    return "Update data failed. Try again later.";
                }
            });
    }

    private void signalOnUpdatedData(ChangeUserListener userListener) {
        int user_id = userListener.getUserId();
        User user = getUserById(user_id);
        if(user != null) {
            userListener.onDataChanged(user);
        } else {
            userListener.onFailed(new ErrorMassage() {
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
                    case ConstantsModel.STATUS_OK:
                        signalUpdatedData();
                        break;
                    case ConstantsModel.STATUS_FAIL:
                        signalFailUpdateData();
                        break;
                }
            }
        };
    }

    private GetUserCallback getDataCallback(final Handler signalHandler) {
        return new GetUserCallback() {
            @Override
            public void onLoadData(List<User> userList) {
                users = userList;
                signalHandler.sendEmptyMessage(ConstantsModel.STATUS_OK);
            }

            @Override
            public void onError() {
                signalHandler.sendEmptyMessage(ConstantsModel.STATUS_FAIL);
            }
        };
    }

}
