package org.jscrapy.core.pipline.impl;

import org.jscrapy.core.config.JscrapyConfig;
import org.jscrapy.core.data.DataItem;
import org.jscrapy.core.pipline.Pipline;

import java.util.List;

/**
 * Created by cxu on 2014/11/21.
 */
public class MongoDbPipline extends Pipline {

    public MongoDbPipline(JscrapyConfig JscrapyConfig)
    {
        super(JscrapyConfig);
    }

    @Override
    public void save(List<DataItem> dataItems) {

    }

}
