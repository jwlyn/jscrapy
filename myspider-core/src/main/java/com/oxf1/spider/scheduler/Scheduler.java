package com.oxf1.spider.scheduler;

import com.oxf1.spider.TaskConfig;
import com.oxf1.spider.component.MyspiderComponent;
import com.oxf1.spider.exception.MySpiderException;
import com.oxf1.spider.request.Request;

import java.util.List;

/**
 * Created by cxu on 2014/11/21.
 */
public abstract class Scheduler extends MyspiderComponent {

    public Scheduler(TaskConfig taskConfig) {
        super(taskConfig);
    }

    /**
     * 将Request插入到请求队列中
     *
     * @param requests
     * @return 插入队列的实际Request数目
     */
    public abstract int push(List<Request> requests) throws MySpiderException;

    /**
     * 从队列中拿出Request
     *
     * @param n 每次拿出多少个Request
     * @return
     */
    public abstract List<Request> poll(int n) throws MySpiderException;

    /**
     * 从队列中删除Request
     *
     * @param requests
     * @return
     */
    public abstract int remove(List<Request> requests);

    public abstract void shutdown();
}
