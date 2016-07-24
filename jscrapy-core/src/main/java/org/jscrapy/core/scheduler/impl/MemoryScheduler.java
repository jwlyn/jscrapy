package org.jscrapy.core.scheduler.impl;

import org.jscrapy.core.TaskConfig;
import org.jscrapy.core.config.cfgkey.ConfigKeys;
import org.jscrapy.core.exception.MySpiderException;
import org.jscrapy.core.request.Request;
import org.jscrapy.core.scheduler.Scheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by cxu on 2015/9/18.
 */
public class MemoryScheduler  extends Scheduler {
    /**
     *
     * @param taskConfig
     */
    public MemoryScheduler(TaskConfig taskConfig) {
        super(taskConfig);
        if(taskConfig.getTaskSharedObject(ConfigKeys._SCHEDULER_MEM_QUEUE_OBJ)==null){
            synchronized (taskConfig){
                if(taskConfig.getTaskSharedObject(ConfigKeys._SCHEDULER_MEM_QUEUE_OBJ)==null){
                    ConcurrentLinkedQueue<Request> queue = new ConcurrentLinkedQueue<Request>();
                    taskConfig.addTaskSharedObject(ConfigKeys._SCHEDULER_MEM_QUEUE_OBJ, queue);
                }
            }
        }
    }

    @Override
    public int push(List<Request> requests) throws MySpiderException {
        ConcurrentLinkedQueue<Request> queue = this.getQueue();
        queue.addAll(requests);
        return requests.size();
    }

    @Override
    public List<Request> poll(int n) throws MySpiderException {
        ConcurrentLinkedQueue<Request> queue = this.getQueue();
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
        return requests.size();
    }

    @Override
    public void close() {
        //do nothing
    }

    private ConcurrentLinkedQueue<Request> getQueue() {
        TaskConfig taskConfig = getTaskConfig();
        ConcurrentLinkedQueue<Request> queue = (ConcurrentLinkedQueue<Request>)this.getTaskConfig().getTaskSharedObject(ConfigKeys._SCHEDULER_MEM_QUEUE_OBJ);
        return queue;
    }
}
