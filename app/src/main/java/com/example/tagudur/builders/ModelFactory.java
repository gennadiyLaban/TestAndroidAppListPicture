package com.example.tagudur.builders;

import com.example.tagudur.builders.abstractions.IModelFactory;
import com.example.tagudur.model.Core;
import com.example.tagudur.model.abstractions.ICoreModel;
import com.example.tagudur.model.web.LoadHandler;

/**
 * Created by Tagudur on 16.02.2018.
 */

public class ModelFactory implements IModelFactory {

    @Override
    public ICoreModel getInstanceModel() {
        Core core = new Core(new LoadHandler());
        return core;
    }
}
