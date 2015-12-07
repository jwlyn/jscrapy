package com.oxf1.myspider.slave.controller;

import com.oxf1.myspider.TaskConfig;
import com.oxf1.myspider.TaskManager;
import com.oxf1.myspider.config.util.ConfigPrepareUtil;
import com.oxf1.myspider.config.util.ConfigValidateUtil;
import com.oxf1.myspider.slave.controller.response.ResponseBase;
import com.oxf1.myspider.exception.MySpiderFetalException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 负责接收管控的指令并利用TaskManager进行任务管理
 * Created by cxu on 2015/10/8.
 */
@RestController
@RequestMapping("/task")
public class TaskControl {
    final static Logger logger = LoggerFactory.getLogger(TaskControl.class);

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
                    ConfigPrepareUtil.prepareConfig(taskConfig);
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
    @RequestMapping("/cancel/{taskId}")
    @ResponseBody
    public String cancelTask(@PathVariable(value = "taskId")String taskId) {
        return "cancel task : "+taskId;
    }

    /**
     * 暂停任务
     * @return
     */
    @RequestMapping("/pause/{taskId}")
    @ResponseBody
    public String pauseTask(@PathVariable(value = "taskId")String taskId) {
        return "pause task : "+ taskId;
    }

}
