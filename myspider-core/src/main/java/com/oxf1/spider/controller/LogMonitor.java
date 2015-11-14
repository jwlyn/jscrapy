package com.oxf1.spider.controller;

import com.oxf1.myspider.common.file.FileLineReader;
import com.oxf1.myspider.common.file.FileLines;
import com.oxf1.spider.TaskManager;
import com.oxf1.spider.controller.response.LogResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * Created by cxu on 2015/11/7.
 */
@Controller
@RequestMapping("/log")
public class LogMonitor {

    @RequestMapping(value = "/task/{taskId}", method = RequestMethod.GET)
    public String index(@PathVariable(value = "taskId")String taskId, Map<String, Object> model){
        model.put("taskId", taskId);
        return "log/index.jsp";
        //return new ModelAndView("log/index.jsp", "taskId", taskId);
    }

    /**
     *
     * @return
     */
    @RequestMapping(value = "/task/{id}/{offset}", method = RequestMethod.GET)
    @ResponseBody
    public LogResponse pollingLog(@PathVariable(value = "id")String taskId, @PathVariable(value = "offset")long byteOffset) {
        LogResponse r = new LogResponse(new Date().toString());

        String logFile = TaskManager.instance().getTaskLogFilePath(taskId);
        logFile = "C:\\code\\play\\task.yaml";
        FileLines lines = null;
        try {
            lines = FileLineReader.readLines(logFile, byteOffset, 5);
        } catch (IOException e) {
            //TODO log
            e.printStackTrace();
            r.setIsSuccess(false);
            r.setErrorReason(e.getLocalizedMessage());
            return r;
        }

        r.setIsSuccess(true);
        r.setLogContent(lines.toString());
        r.setCurOffset(lines.getOffset());
        return r;
    }
}
