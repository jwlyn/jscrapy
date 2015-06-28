package com.oxf1.spider.component;

import com.oxf1.spider.TaskConfig;

/**
 * Created by cxu on 2015/6/22.
 */
public class MyspiderComponent {
    private final TaskConfig taskConfig;

    public MyspiderComponent(TaskConfig taskConfig){
        this.taskConfig = taskConfig;
    }

    protected TaskConfig getTaskConfig(){
        return this.taskConfig;
    }

}
