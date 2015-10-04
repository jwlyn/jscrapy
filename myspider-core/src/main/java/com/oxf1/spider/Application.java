package com.oxf1.spider;

import com.oxf1.spider.util.ConfigValidateUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by cxu on 2015/10/2.
 */
public class Application {
    final static Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[]arg) throws IOException {
        String taskConfigFile = parseTaskConfigFile();

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

    public static String parseTaskConfigFile() {
        String taskConfigFile = System.getProperty("task.config");
        if (StringUtils.isBlank(taskConfigFile)) {
            printUsage();
        }

        return taskConfigFile;
    }

    public static void printUsage() {
        System.out.println("Usage: java -Dtask.config=<path/to/task.yaml> -jar yourJar.jar");
        System.exit(-1);
    }
}
