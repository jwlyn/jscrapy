package com.oxf1.spider;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 管理一个节点上的所有Task，存放Task的状态(status)
 * Created by cxu on 2015/6/20.
 */
public class TaskManager {
    private static TaskManager TASK_MANAGER = new TaskManager();

    private Map<String, Task> tasks;

    private TaskManager() {
        tasks = new ConcurrentHashMap<String, Task>();
    }

    public TaskManager instance(){
        return TASK_MANAGER;
    }

    public void startTask(TaskConfig taskConfig) {
        String taskId = taskConfig.getTaskId();
        Task task = tasks.get(taskId);
        if (task != null) {
            //TODO log
        }
        else {
            task = new Task(taskConfig);
            tasks.put(taskId, task);
            task.run();
        }

    }

    public void cancelTask(TaskConfig taskConfig) {
        String taskId = taskConfig.getTaskId();
        Task task = tasks.get(taskId);
        if (task == null) {
            //TODO log
        }
        else {
            task.cancel();
            tasks.remove(taskId);
        }
    }

    public void pauseTask(TaskConfig taskConfig) {
        String taskId = taskConfig.getTaskId();
        Task task = tasks.get(taskId);
        if (task == null) {
            //TODO log
        }
        else {
            task.pause();
            tasks.remove(taskId);
        }
    }
}
