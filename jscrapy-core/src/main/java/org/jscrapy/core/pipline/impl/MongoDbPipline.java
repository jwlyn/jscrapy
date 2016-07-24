package org.jscrapy.core.pipline.impl;

import org.jscrapy.core.TaskConfig;
import org.jscrapy.core.data.DataItem;
import org.jscrapy.core.pipline.Pipline;

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
