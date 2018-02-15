package com.example.tagudur.model.web.utilits;

import com.example.tagudur.model.entityes.User;
import com.example.tagudur.model.web.abstractions.IUserFactory;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tagudur on 14.02.2018.
 */

public class UserDaraFactory implements IUserFactory {

    @Override
    public List<User> parseJsonUsersData(String json) {
        JsonElement jsonElement = new JsonParser().parse(json);
        JsonArray jArray = jsonElement.getAsJsonObject()
                .getAsJsonArray(IConstantsWeb.NODE_LIST_USERS_JSON_ARRAY);
        List<User> users = new ArrayList<>(IConstantsWeb.DEFUALT_COUNT_OF_PAGE_USERS);
        for (int i = 0; i < jArray.size(); i++) {
            JsonObject jObject = jArray.get(i).getAsJsonObject();
            int id = jObject.get(IConstantsWeb.NODE_ID_JSON_USER).getAsInt();
            String firstName = jObject.get(IConstantsWeb.NODE_FIRST_NAME_JSON_USER).getAsString();
            String lastName = jObject.get(IConstantsWeb.NODE_LAST_NAME_JSON_USER).getAsString();

            User user = new User(id, firstName, lastName);
            users.add(user);
        }

        return users;
    }

    public static IUserFactory getInstance() {
        return new UserDaraFactory();
    }
}
