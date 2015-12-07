package com.oxf1.myspider.slave.controller;

import com.oxf1.myspider.TaskManager;
import com.oxf1.myspider.status.TaskStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by cxu on 2015/10/8.
 */
@Controller
@RequestMapping("/status")
public class TaskStatusMonitor {
    /**
     *
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public List<TaskStatus> allStatus() {
        List<TaskStatus> sts = TaskManager.instance().getTaskStatus();
        return sts;
    }

    /**
     *
     * @param taskId
     * @return
     */
    @RequestMapping(value = "/task/{id}", method = RequestMethod.GET)
    @ResponseBody
    public TaskStatus taskStatus(@PathVariable(value = "id")String taskId) {
        TaskStatus sts = TaskManager.instance().getTaskStatus(taskId);
        return sts;
    }
}
