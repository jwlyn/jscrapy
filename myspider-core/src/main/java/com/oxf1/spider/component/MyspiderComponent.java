package com.oxf1.spider.component;

import com.oxf1.spider.TaskId;

/**
 * Created by cxu on 2015/6/22.
 */
public class MyspiderComponent {
    private final TaskId taskid;

    public MyspiderComponent(TaskId taskid){
        this.taskid = taskid;
    }

    protected TaskId getTaskid(){
        return this.taskid;
    }

}
