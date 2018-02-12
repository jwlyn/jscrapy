package org.jscrapy.core.config.modulecfg;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by cxu on 2017/1/25.
 */
public class H2QueueConfig extends TaskComponentConfig {

    private String queueName;

    //队列的互斥锁， 这个锁会造成一个问题是，如果在一个机器上部署多个Node，运行基于h2队列的任务胡出现多个节点不能互斥
    private ReentrantLock h2QueueLock = new ReentrantLock(); // not a fair lock

    public H2QueueConfig() {

    }

    public ReentrantLock getH2QueueLock() {
        return h2QueueLock;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }
}
