package com.oxf1.spider.exception;

/**
 * Created by cxu on 2015/6/22.
 */
public enum  MySpiderExceptionCode {
    LOCAL_QUEUE_SCHEDULE_ENQUEUE_ERROR("LocalQueueScheduler.java: Request进入队列时出错"),
    LOCAL_QUEUE_SCHEDULE_DEQUEUE_ERROR("LocalQueueScheduler.java: Request出队列时出错"),
    LOCAL_QUEUE_SCHEDULE_DEJSON_ERROR("LocalQueueScheduler.java: Request出队列后反解析json成为Request时出错"),
    LOCAL_QUEUE_SCHEDULE_INIT_ERROR("LocalQueueScheduler.java: 构造本地的bigQueue时出错"),
    ;


    private String errorReason;
    private MySpiderExceptionCode(String errorReason){
        this.errorReason = errorReason;
    }

    public String getErrorReason(){
        return this.errorReason;
    }

}
