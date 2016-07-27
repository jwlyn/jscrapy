package org.jscrapy.core.scheduler.impl;

import org.jscrapy.core.config.JscrapyConfig;
import org.jscrapy.core.exception.MySpiderException;
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
public class DiskScheduler extends Scheduler {
    private BlockingQueue<Request> queue;

    public DiskScheduler(JscrapyConfig JscrapyConfig) {
        super(JscrapyConfig);

        String queueFilePath = this.getFilePath();
        DB db = DBMaker.fileDB(new File(queueFilePath))
                .make();
        queue = db.getQueue("fifo");
    }

    @Override
    public int push(List<Request> requests) throws MySpiderException {
        queue.addAll(requests);
        return requests.size();
    }

    @Override
    public List<Request> poll(int n) throws MySpiderException {
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
        return requests == null ? 0 : requests.size();
    }

    /**
     * @return
     */
    public String getFilePath() {
        JscrapyConfig JscrapyConfig = getJscrapyConfig();
        String spiderWorkDir = JscrapyConfig.getSpiderWorkDir();
        String setFilePath = spiderWorkDir + JscrapyConfig.getTaskFp() + File.separator + "queue" + File.separator + "disk_queue.dump";
        return setFilePath;
    }
}
