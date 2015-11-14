package com.oxf1.myspider.common.log;

import com.oxf1.myspider.common.datetime.DatetimeUtil;
import org.apache.log4j.*;
import org.slf4j.Logger;

import java.io.File;

/**
 * Created by cxu on 2015/10/27.
 */
public class MyLoggerFactory {
    public static Logger getLogger(Class clazz) {
        Logger logger = org.slf4j.LoggerFactory.getLogger(clazz);
        return logger;
    }

    /**
     *
     * @param logPath
     * @return
     */
    public static org.apache.log4j.Logger getModuleLogger(String taskFp, String logPath)
    {
        if(!logPath.endsWith(File.separator)){
            logPath = logPath + File.separator;
        }
        logPath = logPath + DatetimeUtil.getTime("yyyyMMdd") + ".log";

        org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(taskFp);
        logger.removeAllAppenders();
        logger.setLevel(Level.DEBUG);
        logger.setAdditivity(false);//不输出到其他的文件里
        FileAppender appender = new RollingFileAppender();
        Layout layout = new PatternLayout("%d %p [%c] - %m%n");
        appender.setFile(logPath);
        appender.setAppend(true);
        appender.setLayout(layout);
        appender.setEncoding("UTF-8");
        appender.activateOptions();
        logger.addAppender(appender);
        return logger;
    }

}
