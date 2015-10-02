package com.oxf1.spider;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by cxu on 2015/10/2.
 */
public class Application {
    public static void main(String[]arg) {
        String taskConfigFile = parseTaskConfigFile();
        //TODO log it
        

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
