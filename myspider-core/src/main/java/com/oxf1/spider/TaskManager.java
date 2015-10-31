package com.oxf1.spider;

import com.oxf1.spider.exception.MySpiderFetalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 管理一个节点上的所有Task，存放Task的状态(status)
 * Created by cxu on 2015/6/20.
 */
public class TaskManager {
    final static Logger logger = LoggerFactory.getLogger(Application.class);

    private static TaskManager TASK_MANAGER = new TaskManager();

    private Map<String, Task> tasks;

    private TaskManager() {
        tasks = new ConcurrentHashMap<String, Task>();
    }

    public static TaskManager instance(){
        return TASK_MANAGER;
    }

    public void runTask(TaskConfig taskConfig) throws MySpiderFetalException {
        String taskId = taskConfig.getTaskId();
        Task task = tasks.get(taskId);
        if (task != null) {
            logger.warn("task {} 已经存在，不能再次调度启动", task);
        }
        else {
            task = new Task(taskConfig);
            tasks.put(taskId, task);
            task.run();
        }
    }

    public void cancelTask(TaskConfig taskConfig) throws MySpiderFetalException {
        String taskId = taskConfig.getTaskId();
        Task task = tasks.get(taskId);
        if (task == null) {
            logger.warn("task {} 不存在，不能取消", task);
        }
        else {
            task.cancel();
            tasks.remove(taskId);
        }
    }

    public void pauseTask(TaskConfig taskConfig) throws MySpiderFetalException {
        String taskId = taskConfig.getTaskId();
        Task task = tasks.get(taskId);
        if (task == null) {
            logger.warn("task {} 不存在，不能暂停", task);
        }
        else {
            task.pause();
            tasks.remove(taskId);
        }
    }
}
