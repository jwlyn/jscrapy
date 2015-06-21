package com.oxf1.spider.pipline;

import com.oxf1.spider.TaskId;
import com.oxf1.spider.data.DataItem;

/**
 * Created by cxu on 2014/11/21.
 */
public abstract class Pipline {
    private final TaskId taskid;

    public Pipline(TaskId taskid){
        this.taskid = taskid;
    }

    protected TaskId getTaskid(){
        return this.taskid;
    }

    /**
     * 保存解析之后的数据
     * @param dataItem 要保存的数据
     */
    public abstract void save(DataItem dataItem);

    public abstract void close();
}
