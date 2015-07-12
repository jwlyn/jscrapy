package com.oxf1.spider.cacher.impl;

import com.oxf1.spider.TaskConfig;
import com.oxf1.spider.cacher.Cacher;
import com.oxf1.spider.page.Page;
import com.oxf1.spider.request.Request;

/**
 * Created by cxu on 2015/7/12.
 */
public class MongoCacher  extends Cacher {

    public MongoCacher(TaskConfig taskConfig) {
        super(taskConfig);
    }

    @Override
    public Page loadPage(Request request) {
        return null;
    }

    @Override
    public void cachePage(Page page) {

    }
}
