package com.oxf1.spider.scheduler.impl;

import com.oxf1.spider.TaskConfig;
import com.oxf1.spider.request.Request;
import com.oxf1.spider.scheduler.Scheduler;

import java.util.List;

/**
 * Mysql 实现的队列
 * Created by cxu on 2014/11/21.
 */
public class MongoScheduler extends Scheduler{

    public MongoScheduler(TaskConfig taskConfig) {
        super(taskConfig);
    }

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

    @Override
    public void shutdown() {

    }
}
