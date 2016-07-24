package org.jscrapy.worker.controller;

import org.jscrapy.core.TaskManager;
import org.jscrapy.common.file.FileLineReader;
import org.jscrapy.common.file.FileLines;
import org.jscrapy.worker.controller.response.LogResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Date;

/**
 * Created by cxu on 2015/11/7.
 */
@Controller
@RequestMapping("/log")
public class LogMonitor {

    @RequestMapping(value = "/task/{taskId}", method = RequestMethod.GET)
    public ModelAndView index(@PathVariable(value = "taskId")String taskId){
        return new ModelAndView("log_console", "taskId", taskId);
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