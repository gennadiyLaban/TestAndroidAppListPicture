package com.example.tagudur.data.http;

import com.example.tagudur.model.users.User;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tagudur on 14.02.2018.
 */

public class JsonUserMapper implements UserMapper {

    @Override
    public List<User> parseJsonUsersData(String json) {
        JsonElement jsonElement = new JsonParser().parse(json);
        JsonArray jArray = jsonElement.getAsJsonObject()
                .getAsJsonArray(ConstantsWeb.NODE_LIST_USERS_JSON_ARRAY);
        List<User> users = new ArrayList<>(ConstantsWeb.DEFUALT_COUNT_OF_PAGE_USERS);
        for (int i = 0; i < jArray.size(); i++) {
            JsonObject jObject = jArray.get(i).getAsJsonObject();
            int id = jObject.get(ConstantsWeb.NODE_ID_JSON_USER).getAsInt();
            String firstName = jObject.get(ConstantsWeb.NODE_FIRST_NAME_JSON_USER).getAsString();
            String lastName = jObject.get(ConstantsWeb.NODE_LAST_NAME_JSON_USER).getAsString();

            User user = new User(id, firstName, lastName);
            users.add(user);
        }

        return users;
    }

    public static UserMapper getInstance() {
        return new JsonUserMapper();
    }
}
