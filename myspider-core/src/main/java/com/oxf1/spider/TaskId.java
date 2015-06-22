package com.oxf1.spider;

/**
 * Created by cxu on 2015/6/21.
 */
public class TaskId {
    private final String id;//唯一标识
    private final String taskName;

    public TaskId(String id, String taskName){
        this.id = id;
        this.taskName = taskName;
    }

    public String getId() {
        return id;
    }

    public String getTaskName(){
        return this.taskName;
    }
}
