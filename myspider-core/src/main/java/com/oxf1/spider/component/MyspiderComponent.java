package com.oxf1.spider.component;

import com.oxf1.spider.TaskConfig;
import com.oxf1.spider.exception.MySpiderRecoverableException;

/**
 * Created by cxu on 2015/6/22.
 */
public abstract class MyspiderComponent {
    private final TaskConfig taskConfig;

    public MyspiderComponent(TaskConfig taskConfig){
        this.taskConfig = taskConfig;
    }
    public abstract void close() throws MySpiderRecoverableException;

    protected TaskConfig getTaskConfig(){
        return this.taskConfig;
    }

}
