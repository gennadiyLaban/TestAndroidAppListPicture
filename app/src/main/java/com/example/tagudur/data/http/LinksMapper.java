package com.example.tagudur.data.http;

import java.util.List;

/**
 * Created by Tagudur on 14.02.2018.
 */

public interface LinksMapper {

    public List<String> parseJsonLinks(String json);

}
