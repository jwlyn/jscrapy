package com.oxf1.spider.controller;

import com.oxf1.spider.TaskConfig;
import com.oxf1.spider.TaskManager;
import com.oxf1.spider.controller.response.ResponseBase;
import com.oxf1.spider.exception.MySpiderFetalException;
import com.oxf1.spider.util.ConfigValidateUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 负责接收管控的指令并利用TaskManager进行任务管理
 * Created by cxu on 2015/10/8.
 */
@Controller
@RequestMapping("/task")
public class TaskListener {
    final static Logger logger = LoggerFactory.getLogger(TaskListener.class);

    /**
     * 启动一个任务
     * @return
     */
    @RequestMapping(value = "/start", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBase startTask(@RequestParam(value = "taskConfig", required = true) String yamlContent) {
        ResponseBase response = new ResponseBase();
        if (StringUtils.isNotBlank(yamlContent)) {
            String tempDir = System.getProperty("java.io.tmpdir");
            String uuid = UUID.randomUUID().toString();
            String file = tempDir + File.separator + uuid + ".yaml";
            try {
                FileUtils.writeStringToFile(new File(file), yamlContent, false);
                TaskConfig taskConfig = new TaskConfig(file);
                StringBuffer errorBuffer = new StringBuffer();
                if (!ConfigValidateUtil.validate(taskConfig, errorBuffer)) {
                    logger.error("校验配置失败!");
                    return new ResponseBase(false, "not-set-yet", errorBuffer.toString());
                }else{//验证通过
                    logger.info("配置校验通过！启动任务");
                    TaskManager.instance().runTask(taskConfig);
                    return new ResponseBase(true, "not-set-yet", "启动成功");
                }

            } catch (MySpiderFetalException e) {
                return new ResponseBase(false, "not-set-yet", e.getErrorMessage());
            } catch (IOException e) {
                return new ResponseBase(false, "not-set-yet", e.getLocalizedMessage());
            } catch (Exception e) {
                return new ResponseBase(false, "not-set-yet", e.getLocalizedMessage());
            }
        }else{
            return new ResponseBase(false, "not-set-yet", "配置内容为空");
        }
    }

    /**
     * 取消一个任务
     * @return
     */
    @RequestMapping("/cancel")
    @ResponseBody
    public String cancelTask() {
        return "cancel task";
    }

    /**
     * 暂停任务
     * @return
     */
    @RequestMapping("/pause")
    @ResponseBody
    public String pauseTask() {
        return "pause task";
    }

}
