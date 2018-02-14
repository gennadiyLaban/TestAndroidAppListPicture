package com.example.tagudur.model.web;

import android.graphics.Bitmap;

import com.example.tagudur.model.IDataListener;
import com.example.tagudur.model.IDataRepository;
import com.example.tagudur.model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Tagudur on 14.02.2018.
 */

public class LoadHandler implements IDataRepository{
    private IDataListener listener;
    private OkHttpClient httpClient;

    @Override
    public void getUserData(IDataListener listener) {
        this.listener = listener;
        httpClient = new OkHttpClient();

        try {
            listener.onLoadData(loadUserData());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private List<User> loadUserData() throws IOException {
        List<User> users = new ArrayList<>(IConstantsWeb.DEFUALT_COUNT_OF_TOTAL_USERS);
        Request.Builder builder = new Request.Builder();
        IUserFactory userFactory = UserDaraFactory.getInstance();

        for(int i = 1; i <= IConstantsWeb.COUNT_OF_PAGE_USERS_DATA; i++) {
            Request request = builder.url(IConstantsWeb.URL_LIST_USER_DATA_ON_PAGE_N + i)
                    .build();
            Call call = httpClient.newCall(request);
            String json = call.execute().body().string();
            List<User> usersPerPage = userFactory.parseJsonUsersData(json);
            users.addAll(usersPerPage);
        }

        return users;
    }

    private List<String> loadListData() {


        return null;
    }

    private List<Bitmap> loadImageData(List<String> urls) {



        return null;
    }



}
