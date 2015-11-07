package com.oxf1.spider.scheduler.impl;

import com.alibaba.fastjson.JSONException;
import com.leansoft.bigqueue.BigQueueImpl;
import com.leansoft.bigqueue.IBigQueue;
import com.oxf1.spider.TaskConfig;
import com.oxf1.spider.config.ConfigKeys;
import com.oxf1.spider.exception.MySpiderExceptionCode;
import com.oxf1.spider.exception.MySpiderFetalException;
import com.oxf1.spider.request.Request;
import com.oxf1.spider.request.impl.HttpRequest;
import com.oxf1.spider.scheduler.Scheduler;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 建立在bigqueue之上的本地请求队列
 * bigqueue:https://github.com/bulldog2011/bigqueue
 * Created by cxu on 2015/6/22.
 */
public class FileQueueScheduler extends Scheduler {

    private String queueFilePath;
    private String queueName;
    private IBigQueue bigQueue;

    public FileQueueScheduler(TaskConfig taskConfig) throws MySpiderFetalException {
        super(taskConfig);

        String spiderWorkDir = taskConfig.getSpiderWorkDir();
        this.queueFilePath = spiderWorkDir + taskConfig.getTaskFp() + File.separator + "scheduler" + File.separator;
        this.queueName = taskConfig.getTaskName();
        taskConfig.put(ConfigKeys.RT_LOCAL_QUEUE_DIR, queueFilePath);
        try{
            bigQueue = new BigQueueImpl(queueFilePath, queueName);
        }catch(IOException e){
            throw new MySpiderFetalException(MySpiderExceptionCode.LOCAL_QUEUE_SCHEDULE_INIT_ERROR);
        }

    }

    @Override
    public int push(List<Request> requests) throws MySpiderFetalException {
        for(Request req : requests){
            String json = req.asJson();
            if(StringUtils.isNotBlank(json)){
                try {
                    bigQueue.enqueue(json.getBytes());
                }catch(IOException e){
                    throw new MySpiderFetalException(MySpiderExceptionCode.LOCAL_QUEUE_SCHEDULE_ENQUEUE_ERROR);
                }
            }
        }
        return requests.size();
    }

    @Override
    public List<Request> poll(int n) throws MySpiderFetalException{
        List<Request> req = new ArrayList<Request>(n);
        for(int i=0; i<n; i++){
            byte[] jsonBytes = null;
            try{
                jsonBytes = bigQueue.dequeue();
            }catch(IOException e){
                throw new MySpiderFetalException(MySpiderExceptionCode.LOCAL_QUEUE_SCHEDULE_DEQUEUE_ERROR);
            }

            if(jsonBytes!=null){
                String json = new String(jsonBytes);
                try{
                    Request request = HttpRequest.build(json, HttpRequest.class);
                    req.add(request);
                }catch(JSONException e){
                    throw new MySpiderFetalException(MySpiderExceptionCode.LOCAL_QUEUE_SCHEDULE_DEJSON_ERROR);
                }
            }
        }
        return req;
    }

    @Override
    public int remove(List<Request> requests) {
        return requests.size();//什么也不干
    }

    @Override
    public void close() {
        if(bigQueue!=null){
            bigQueue.flush();
            try {
                bigQueue.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
