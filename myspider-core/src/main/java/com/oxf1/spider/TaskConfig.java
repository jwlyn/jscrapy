package com.oxf1.spider;

import com.oxf1.spider.config.ConfigOperator;
import com.oxf1.spider.config.impl.EhcacheConfigOperator;

/**
 * Created by cxu on 2015/6/21.
 */
public class TaskConfig implements ConfigOperator{
    private final String taskId;//唯一标识
    private final String taskName;
    private final ConfigOperator cfg;

    public TaskConfig(String id, String taskName, ConfigOperator cfg){
        this.taskId = id;
        this.taskName = taskName;
        this.cfg = cfg;
    }

    public TaskConfig(String taskId, String taskName){
        this.taskId = taskId;
        this.taskName = taskName;
        this.cfg = new EhcacheConfigOperator();//默认的
    }

    public String getTaskId() {
        return this.taskId;
    }

    public String getTaskName(){
        return this.taskName;
    }

    @Override
    public String loadString(TaskConfig taskConfig, String key) {
        return this.cfg.loadString(taskConfig, key);
    }

    @Override
    public Integer loadInt(TaskConfig taskConfig, String key) {
        return this.cfg.loadInt(taskConfig, key);
    }

    @Override
    public void put(TaskConfig taskConfig, String key, Object value) {
        this.cfg.put(taskConfig, key, value);
    }
}
