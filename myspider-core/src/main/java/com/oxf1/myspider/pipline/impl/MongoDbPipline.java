package com.oxf1.myspider.pipline.impl;

import com.oxf1.myspider.TaskConfig;
import com.oxf1.myspider.data.DataItem;
import com.oxf1.myspider.pipline.Pipline;

import java.util.List;

/**
 * Created by cxu on 2014/11/21.
 */
public class MongoDbPipline extends Pipline {

    public MongoDbPipline(TaskConfig taskConfig)
    {
        super(taskConfig);
    }

    @Override
    public void save(List<DataItem> dataItems) {

    }


    @Override
    public void close() {

    }
}
