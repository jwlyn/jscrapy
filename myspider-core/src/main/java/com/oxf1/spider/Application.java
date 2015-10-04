package com.oxf1.spider;

import com.oxf1.spider.util.ConfigValidateUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * Created by cxu on 2015/10/2.
 */
@Controller
@EnableAutoConfiguration
public class Application {
    final static Logger logger = LoggerFactory.getLogger(Application.class);

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World!";
    }
    public static void main(String[]args) throws IOException {
        //TODO 把过程变成，如果用户传递了文件而且存在，直接启动Web，然后post这个请求到入口。

        String taskConfigFile = parseTaskConfigFile();
        if (StringUtils.isNotBlank(taskConfigFile)) {
            TaskConfig taskConfig = new TaskConfig(taskConfigFile);
            if (!ConfigValidateUtil.validate(taskConfig)) {
                logger.info("请按照提示检查配置!");
                return;
            }else{
                logger.info("配置校验通过！");
            }
            //验证通过

            System.exit(0);
        }
        else{
            SpringApplication.run(Application.class, args);
        }
    }

    public static String parseTaskConfigFile() {
        String taskConfigFile = System.getProperty("task.config");
        return taskConfigFile;
    }

    public static void printUsage() {
        System.out.println("Usage: java -Dtask.config=<path/to/task.yaml> -jar yourJar.jar");
        System.exit(-1);
    }
}
