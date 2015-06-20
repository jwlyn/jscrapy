package com.oxf1.spider.scheduler.impl;

import com.oxf1.spider.request.Request;
import com.oxf1.spider.scheduler.Scheduler;

import java.util.List;

/**
 * Created by cxu on 2015/6/21.
 */
public class RedisScheduler  implements Scheduler {
    @Override
    public int push(List<Request> requests) {
        return 0;
    }

    @Override
    public List<Request> poll(int n) {
        return null;
    }

    @Override
    public int remove(List<Request> requests) {
        return 0;
    }
}
