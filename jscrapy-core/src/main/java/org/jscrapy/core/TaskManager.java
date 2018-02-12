package org.jscrapy.core;

import org.jscrapy.core.config.JscrapyConfig;
import org.jscrapy.core.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 管理一个节点上的所有Task，存放Task的状态(status)
 * Created by cxu on 2015/6/20.
 */
public class TaskManager {
    final static Logger logger = LoggerFactory.getLogger(TaskManager.class);

    private static TaskManager TASK_MANAGER = new TaskManager();

    private Map<String, Task> tasks = new ConcurrentHashMap<String, Task>();
    ;

    public static TaskManager instance() {
        return TASK_MANAGER;
    }

    public void runTask(JscrapyConfig JscrapyConfig) {
        String taskId = JscrapyConfig.getTaskId();
        Task task = tasks.get(taskId);
        if (task != null) {
            logger.warn("task {} 已经存在，不能再次调度启动", task);
        } else {
            task = new Task(JscrapyConfig);
            tasks.put(taskId, task);
            task.run();
        }
    }

    public void cancelTask(JscrapyConfig JscrapyConfig) {
        String taskId = JscrapyConfig.getTaskId();
        Task task = tasks.get(taskId);
        if (task == null) {
            logger.warn("task {} 不存在，不能取消", task);
        } else {
            task.cancel();
            tasks.remove(taskId);
        }
    }

    public void pauseTask(JscrapyConfig JscrapyConfig) {
        String taskId = JscrapyConfig.getTaskId();
        Task task = tasks.get(taskId);
        if (task == null) {
            logger.warn("task {} 不存在，不能暂停", task);
        } else {
            task.pause();
            tasks.remove(taskId);
        }
    }

//    public List<TaskStatus> getTaskStatus() {
//        List<TaskStatus> status = new ArrayList<>();
//        Set<String> taskIds = tasks.keySet();
//        for (String taskId : taskIds) {
//            Task task = tasks.get(taskId);
//            if (task != null) {
//                TaskStatus sts = task.getJscrapyConfig().getTaskStatusObject();
//                status.add(sts);
//            }
//        }
//
//        return status;
//    }

//    public TaskStatus getTaskStatus(String taskId) {
//        TaskStatus status = null;
//
//        Task task = tasks.get(taskId);
//        if (task != null) {
//            status = task.getJscrapyConfig().getTaskStatusObject();
//        }
//
//        return status;
//    }

//    /**
//     * 没找到返回null
//     * @param taskId
//     * @return
//     */
//    public String getTaskLogFilePath(String taskId) {
//        String logFilePath = null;
//        Task task = tasks.get(taskId);
//        if (task != null) {
//            logFilePath = task.getJscrapyConfig().getTaskLogDir() + File.separator + DatetimeUtil.getTime("yyyyMMdd") + ".log";
//        }
//
//        return logFilePath;
//    }
}
