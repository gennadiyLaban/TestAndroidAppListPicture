package com.example.tagudur.data.http;

import com.example.tagudur.model.users.GetUserCallback;
import com.example.tagudur.model.users.UserRepository;
import com.example.tagudur.model.users.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Tagudur on 14.02.2018.
 */

public class HttpUsersRepository implements UserRepository {
    private OkHttpClient httpClient;
    private ExecutorService executor = Executors.newFixedThreadPool(42);

    @Override
    public void getUserData(final GetUserCallback listener) {
        if(httpClient == null) {
            httpClient = new OkHttpClient();
        }

        try {
            Future<ApiListResponse<User>> futureUsers = executor.submit(new Callable<ApiListResponse<User>>() {
                @Override
                public ApiListResponse<User> call() throws Exception {
                    ApiListResponse<User> response = loadUserData();
                    return response;
                }
            });

            Future<ApiListResponse<String>> futureLinks = executor.submit(new Callable<ApiListResponse<String>>() {
                @Override
                public ApiListResponse<String> call() throws Exception {
                    ApiListResponse<String> response = loadUrlListImages();
                    return response;
                }
            });
            ApiListResponse<String> responseLinks = futureLinks.get();
            ApiListResponse<User> responseUserData = futureUsers.get();

            if(responseLinks.result == ApiListResponse.Result.ERROR ||
                    responseUserData.result == ApiListResponse.Result.ERROR) {
                listener.onError();
                return;
            } else {
                listener.onLoadData(uniteUserAndLinks(responseUserData.list, responseLinks.list));
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


    private ApiListResponse<User> loadUserData() throws IOException {
        List<User> users = new ArrayList<>(ConstantsWeb.DEFUALT_COUNT_OF_TOTAL_USERS);
        Request.Builder builder = new Request.Builder();
        UserMapper userFactory = JsonUserMapper.getInstance();

        for(int i = 1; i <= ConstantsWeb.COUNT_OF_PAGE_USERS_DATA; i++) {
            Request request = builder.url(ConstantsWeb.URL_LIST_USER_DATA_ON_PAGE_N + i)
                    .build();
            Response response = executeRequest(request);
            if(response.code() < 200 || response.code() >= 300) {
                return new ApiListResponse<User>(ApiListResponse.Result.ERROR, null);
            }

            String json = response.body().string();
            List<User> usersPerPage = userFactory.parseJsonUsersData(json);
            users.addAll(usersPerPage);
        }

        return new ApiListResponse<User>(ApiListResponse.Result.SUCCESS, users);
    }

    // todo: make request from echo server
    private ApiListResponse<String> loadUrlListImages() throws IOException {
        Request.Builder builder = new Request.Builder();
        Request request = builder
                .url(ConstantsWeb.URL_LIST_PICTURE_LINKS)
                .build();
        Response response = executeRequest(request);
        if(response.code() < 200 || response.code() >= 300) {
            return new ApiListResponse<String>(ApiListResponse.Result.ERROR, null);
        }

        String json = response.body().string();

        LinksMapper linksFactory = JsonLinksMapper.getInstance();
        List<String> linksList = linksFactory.parseJsonLinks(json);

        return new ApiListResponse<String>(ApiListResponse.Result.SUCCESS, linksList);
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
        return response;
    }

    private static class ApiListResponse<T> {
        public final Result result;
        public final List<T> list;

        public ApiListResponse(Result result, List<T> list) {
            this.result = result;
            this.list = list;
        }

        enum Result {
            SUCCESS, ERROR
        };
    };
}
