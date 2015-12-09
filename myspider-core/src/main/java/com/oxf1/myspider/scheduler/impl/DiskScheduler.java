package com.oxf1.myspider.scheduler.impl;

import com.oxf1.myspider.TaskConfig;
import com.oxf1.myspider.config.ConfigKeys;
import com.oxf1.myspider.exception.MySpiderException;
import com.oxf1.myspider.exception.MySpiderRecoverableException;
import com.oxf1.myspider.request.Request;
import com.oxf1.myspider.scheduler.Scheduler;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 基于mapdb的单机大队列
 * Created by cxu on 2015/12/8.
 */
public class DiskScheduler  extends Scheduler {

    public DiskScheduler(TaskConfig taskConfig) {
        super(taskConfig);
        if(taskConfig.getTaskSharedObject(ConfigKeys.DISK_DEDUP_SET)==null){
            synchronized (taskConfig){
                if(taskConfig.getTaskSharedObject(ConfigKeys.DISK_SCHEDULER_QUEUE)==null){
                    String queueFilePath = this.getFilePath();
                    DB db = DBMaker.fileDB(new File(queueFilePath))
                            //.cacheSize(100000)
                            .make();
                    BlockingQueue<Request> queue = db.getQueue("fifo");//TODO
                    taskConfig.addTaskSharedObject(ConfigKeys.DISK_SCHEDULER_QUEUE, queue);
                }
            }
        }
    }

    @Override
    public int push(List<Request> requests) throws MySpiderException {
        BlockingQueue<Request> queue = this.getQueue();
        queue.addAll(requests);
        return requests.size();
    }

    @Override
    public List<Request> poll(int n) throws MySpiderException {
        BlockingQueue<Request> queue = this.getQueue();
        List<Request> requests = new ArrayList<Request>(n);
        for (int i = 0; i < n; i++) {
            Request req = queue.poll();
            if (req == null) {
                break;
            }
            requests.add(req);
        }
        return requests;
    }

    @Override
    public int remove(List<Request> requests) {
        return requests==null ? 0 : requests.size();
    }

    @Override
    public void close() throws MySpiderRecoverableException {

    }

    /**
     *
     * @return
     */
    public String getFilePath() {
        TaskConfig taskConfig = getTaskConfig();
        String spiderWorkDir = taskConfig.getSpiderWorkDir();
        String setFilePath = spiderWorkDir + taskConfig.getTaskFp() + File.separator + "queue" + File.separator + "disk_queue.dump";
        return setFilePath;
    }

    /**
     *
     * @return
     */
    private BlockingQueue<Request> getQueue() {
        TaskConfig taskConfig = this.getTaskConfig();
        BlockingQueue<Request> queue = (BlockingQueue<Request>)taskConfig.getTaskSharedObject(ConfigKeys.DISK_SCHEDULER_QUEUE);
        return queue;
    }
}
