package com.example.tagudur.model.web;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tagudur on 14.02.2018.
 */

public class LinksFactory implements ILinksFactory {

    @Override
    public List<String> parseJsonLinks(String json) {
        JsonObject root = new JsonParser().parse(json).getAsJsonObject();
        JsonArray jsonLinkList = root.get(IConstantsWeb.NODE_DATA_JSON_LINKS).getAsJsonArray();

        List<String> linkList = new ArrayList<>(IConstantsWeb.DEFUALT_COUNT_OF_TOTAL_USERS);
        for (int i = 0; i < jsonLinkList.size(); i++) {
            String link = jsonLinkList.get(i).getAsString();
            linkList.add(link);
        }

        return linkList;
    }

    public static ILinksFactory getInstance() {
        return new LinksFactory();
    }
}
