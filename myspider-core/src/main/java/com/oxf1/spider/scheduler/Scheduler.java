package com.oxf1.spider.scheduler;

import com.oxf1.spider.request.Request;

import java.util.List;

/**
 * Created by cxu on 2014/11/21.
 */
public interface Scheduler {

    /**
     * 将Request插入到请求队列中
     * @param requests
     * @return 插入队列的实际Request数目
     */
    public int push(List<Request> requests);

    /**
     * 从队列中拿出Request
     * @param n 每次拿出多少个Request
     * @return
     */
    public List<Request> poll(int n);

    /**
     * 从队列中删除Request
     * @param requests
     * @return
     */
    public int remove(List<Request> requests);

}
