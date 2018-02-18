package com.example.tagudur.model.http;

import java.util.List;

/**
 * Created by Tagudur on 14.02.2018.
 */

public interface LinksFactory {

    public List<String> parseJsonLinks(String json);

}
