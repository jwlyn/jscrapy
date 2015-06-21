package com.oxf1.spider.pipline.impl;

import com.oxf1.spider.TaskId;
import com.oxf1.spider.data.DataItem;
import com.oxf1.spider.pipline.Pipline;

/**
 * Created by cxu on 2014/11/21.
 */
public class MongoDbPipline extends Pipline {

    public MongoDbPipline(TaskId taskid)
    {
        super(taskid);
    }

    @Override
    public void save(DataItem dataItem) {

    }

    @Override
    public void close() {

    }
}
