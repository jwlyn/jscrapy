package com.oxf1.spider.dudup.impl;

import com.oxf1.spider.TaskConfig;
import com.oxf1.spider.dudup.DeDup;
import com.oxf1.spider.request.Request;

/**
 * Created by cxu on 2015/9/18.
 */
public class MemoryDedup  extends DeDup {
    public MemoryDedup(TaskConfig taskConfig) {
        super(taskConfig);
    }

    @Override
    protected boolean isDup(Request request) {
        return false;
    }

    @Override
    public void close() {

    }
}
