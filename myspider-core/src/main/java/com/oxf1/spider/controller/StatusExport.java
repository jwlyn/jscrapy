package com.oxf1.spider.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by cxu on 2015/10/8.
 */
@Controller
@RequestMapping("/status")
public class StatusExport {
    /**
     *
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public String allStatus() {
        return "all status";
    }

    @RequestMapping(value = "/task/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String taskStatus(@PathVariable(value = "id")String taskId) {
        return "task "+taskId;
    }
}
