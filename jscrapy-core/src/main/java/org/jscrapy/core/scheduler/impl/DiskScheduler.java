package org.jscrapy.core.scheduler.impl;

import org.jscrapy.core.TaskConfig;
import org.jscrapy.core.config.cfgkey.ConfigKeys;
import org.jscrapy.core.exception.MySpiderException;
import org.jscrapy.core.exception.MySpiderRecoverableException;
import org.jscrapy.core.request.Request;
import org.jscrapy.core.scheduler.Scheduler;
import org.mapdb.DB;
import org.mapdb.DBMaker;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * 基于mapdb的单机大队列
 * Created by cxu on 2015/12/8.
 */
public class DiskScheduler  extends Scheduler {

    public DiskScheduler(TaskConfig taskConfig) {
        super(taskConfig);
        if(taskConfig.getTaskSharedObject(ConfigKeys._DEDUP_DISK_SET_OBJ)==null){
            synchronized (taskConfig){
                if(taskConfig.getTaskSharedObject(ConfigKeys._SCHEDULER_DISK_QUEUE_OBJ)==null){
                    String queueFilePath = this.getFilePath();
                    DB db = DBMaker.fileDB(new File(queueFilePath))
                            //.cacheSize(100000)
                            .make();
                    BlockingQueue<Request> queue = db.getQueue("fifo");//TODO
                    taskConfig.addTaskSharedObject(ConfigKeys._SCHEDULER_DISK_QUEUE_OBJ, queue);
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
        BlockingQueue<Request> queue = (BlockingQueue<Request>)taskConfig.getTaskSharedObject(ConfigKeys._SCHEDULER_DISK_QUEUE_OBJ);
        return queue;
    }
}
