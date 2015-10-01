package com.oxf1.spider.pipline.impl;

import com.oxf1.spider.TaskConfig;
import com.oxf1.spider.data.DataItem;
import com.oxf1.spider.pipline.Pipline;

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
