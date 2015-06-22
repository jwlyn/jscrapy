package com.oxf1.spider.dudup.impl;

import com.oxf1.spider.TaskId;
import com.oxf1.spider.dudup.DeDup;
import com.oxf1.spider.request.Request;

import java.util.List;

/**
 * Created by cxu on 2015/6/22.
 */
public class RedisDedup extends DeDup {
    public RedisDedup(TaskId taskid) {
        super(taskid);
    }

    @Override
    public boolean isDup(Request request) {
        return false;
    }

    @Override
    public List<Request> deDup(List<Request> request) {
        return null;
    }
}
