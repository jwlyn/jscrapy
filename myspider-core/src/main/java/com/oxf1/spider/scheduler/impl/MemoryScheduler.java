package com.oxf1.spider.scheduler.impl;

import com.oxf1.spider.TaskConfig;
import com.oxf1.spider.exception.MySpiderException;
import com.oxf1.spider.request.Request;
import com.oxf1.spider.scheduler.Scheduler;

import java.util.List;

/**
 * Created by cxu on 2015/9/18.
 */
public class MemoryScheduler  extends Scheduler {
    public MemoryScheduler(TaskConfig taskConfig) {
        super(taskConfig);
    }

    @Override
    public int push(List<Request> requests) throws MySpiderException {
        return 0;
    }

    @Override
    public List<Request> poll(int n) throws MySpiderException {
        return null;
    }

    @Override
    public int remove(List<Request> requests) {
        return 0;
    }

    @Override
    public void close() {
        //do nothing
    }
}
