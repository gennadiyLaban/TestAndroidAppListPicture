package com.example.tagudur.model.http;

import com.example.tagudur.model.usercase.GetUserCallback;
import com.example.tagudur.model.usercase.UserRepository;
import com.example.tagudur.model.usercase.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Tagudur on 14.02.2018.
 */

public class HttpUsersRepository implements UserRepository {
    private OkHttpClient httpClient;

    private boolean error = false;
    private int codeError = -1;

    @Override
    public void getUserData(GetUserCallback listener) {
        if(httpClient == null) {
            httpClient = new OkHttpClient();
        }
        error = false;
        codeError = -1;

        List<User> userList = new ArrayList<>();
        try {
            userList = loadUserData();
            if(error){
                listener.onError();
                return;
            }

            List<String> linksList = loadUrlListImages();
            if(error){
                listener.onError();
                return;
            }

            userList = uniteUserAndLinks(userList, linksList);
            userList = loadImageData(userList);
            if(error){
                listener.onError();
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
            listener.onError();
        }

        listener.onLoadData(userList);
    }


    private List<User> loadUserData() throws IOException {
        List<User> users = new ArrayList<>(ConstantsWeb.DEFUALT_COUNT_OF_TOTAL_USERS);
        Request.Builder builder = new Request.Builder();
        UserMapper userFactory = JsonUserMapper.getInstance();

        for(int i = 1; i <= ConstantsWeb.COUNT_OF_PAGE_USERS_DATA; i++) {
            Request request = builder.url(ConstantsWeb.URL_LIST_USER_DATA_ON_PAGE_N + i)
                    .build();
            Response response = executeRequest(request);
            if(error)
                return users;

            String json = response.body().string();
            List<User> usersPerPage = userFactory.parseJsonUsersData(json);
            users.addAll(usersPerPage);
        }

        return users;
    }

    // todo: make request from echo server
    private List<String> loadUrlListImages() throws IOException {
        Request.Builder builder = new Request.Builder();
        Request request = builder
                .url(ConstantsWeb.URL_LIST_PICTURE_LINKS)
                .build();
        Response response = executeRequest(request);
        if(error)
            return new ArrayList<>();

        String json = response.body().string();

        LinksMapper linksFactory = JsonLinksMapper.getInstance();
        List<String> linksList = linksFactory.parseJsonLinks(json);

        return linksList;
    }

    private List<User> loadImageData(List<User> users) throws IOException {
        for (User user: users) {
            byte[] imageBytes = loadImageAsByteArray(user.getUrlPicture());
            user.setPicture(imageBytes);

            if(error)
                break;
        }

        return users;
    }

    private byte[] loadImageAsByteArray(String urlLink) throws IOException {
        byte[] imageBytes = new byte[1];
        if(urlLink == null || urlLink.length() == 0) {
            return imageBytes;
        }
        Request.Builder builder = new Request.Builder();
        Request request = builder
                .url(urlLink)
                .build();
        Response response = executeRequest(request);
        if(error)
            return imageBytes;

        imageBytes = response.body().bytes();
        return imageBytes;
    }

    private List<User> uniteUserAndLinks(List<User> users, List<String> links) {
        Iterator<User> userIterator = users.iterator();
        Iterator<String> linksIterator = links.iterator();
        while (userIterator.hasNext() && linksIterator.hasNext()) {
            User user = userIterator.next();
            String link = linksIterator.next();
            user.setUrlPicture(link);
        }

        return users;
    }

    private Response executeRequest(Request request) throws IOException {
        Call call = httpClient.newCall(request);
        Response response = call.execute();
        int code = response.code();
        error = (code < 200 || code > 299);
        if(error) {
            codeError = code;
        }
        return response;
    }

}
