package com.example.tagudur.data.http;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tagudur on 14.02.2018.
 */

public class JsonLinksMapper implements LinksMapper {

    @Override
    public List<String> parseJsonLinks(String json) {
        JsonObject root = new JsonParser().parse(json).getAsJsonObject();
        JsonArray jsonLinkList = root.get(ConstantsWeb.NODE_DATA_JSON_LINKS).getAsJsonArray();

        List<String> linkList = new ArrayList<>(ConstantsWeb.DEFUALT_COUNT_OF_TOTAL_USERS);
        for (int i = 0; i < jsonLinkList.size(); i++) {
            String link = jsonLinkList.get(i).getAsString();
            linkList.add(link);
        }

        return linkList;
    }

    public static LinksMapper getInstance() {
        return new JsonLinksMapper();
    }
}
