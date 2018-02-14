package com.example.tagudur.model.web;

import java.util.List;

/**
 * Created by Tagudur on 14.02.2018.
 */

public interface ILinksFactory {

    public List<String> parseJsonLinks(String json);

}
