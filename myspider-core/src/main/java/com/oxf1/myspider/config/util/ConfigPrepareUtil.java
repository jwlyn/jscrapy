package com.oxf1.myspider.config.util;

import com.oxf1.myspider.TaskConfig;
import com.oxf1.myspider.config.cfgkey.ConfigKeys;
import com.oxf1.myspider.exception.MySpiderFetalException;
import org.apache.commons.lang3.StringUtils;

import java.io.File;

/**
 * 准备好一些运行中才产生的配置，事先设置好
 * Created by cxu on 2015/11/7.
 */
public class ConfigPrepareUtil {
    /**
     *
     * @param taskConfig
     */
    public static void prepareConfig(TaskConfig taskConfig) throws MySpiderFetalException {
        prepareTaskWorkDir(taskConfig);
        prepareTaskCacheDir(taskConfig);
        prepareTaskLogDir(taskConfig);
    }

    private static void prepareTaskWorkDir(TaskConfig taskConfig) throws MySpiderFetalException {
        String spiderWorkDir = taskConfig.getSpiderWorkDir();
        if(StringUtils.isNotBlank(spiderWorkDir)){
            String taskWorkDir = spiderWorkDir + taskConfig.getTaskFp() + File.separator;
            taskConfig.put(ConfigKeys.RT_EXT_RT_LOCAL_TASK_WORK_DIR, taskWorkDir);
        }
    }

    private static void prepareTaskCacheDir(TaskConfig taskConfig) throws MySpiderFetalException {
        String spiderWorkDir = taskConfig.getSpiderWorkDir();
        if(StringUtils.isNotBlank(spiderWorkDir)){
            String taskCacheDir = spiderWorkDir + taskConfig.getTaskFp() + File.separator+ "cacher" + File.separator;;
            taskConfig.put(ConfigKeys.RT_EXT_RT_LOCAL_TASK_CACHER_DIR, taskCacheDir);
        }
    }

    private static void prepareTaskLogDir(TaskConfig taskConfig) throws MySpiderFetalException {
        String logDir = taskConfig.getTaskWorkDir() + "logs" + File.separator;
        taskConfig.put(ConfigKeys.RT_EXT_RT_TASK_LOG_DIR, logDir);
    }
}
