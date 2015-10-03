package com.oxf1.spider.util;

import com.oxf1.spider.TaskConfig;
import com.oxf1.spider.config.ConfigKeys;

/**
 * 从配置文件加载一个任务之前利用这个类校验参数的完备性和合法性
 *
 * Created by cxu on 2015/10/3.
 */
public class ConfigValidateUtil {
    public static boolean validate(TaskConfig taskConfig) {
        boolean result = true;
        //验证必须的参数
        String[] requiredParameters = {ConfigKeys.TASK_ID,
                ConfigKeys.TASK_NAME,
                ConfigKeys.VIRTUAL_ID,
                ConfigKeys.SPIDER_WORK_DIR,
                ConfigKeys.WAIT_URL_SLEEP_TIME_MS,
                ConfigKeys.TASK_STATUS,
                ConfigKeys.SCHEDULER_BATCH_SIZE,
                ConfigKeys.THREAD_COUNT,
                ConfigKeys.SCHEDULER_CLASS_NAME,
                ConfigKeys.DEDUP_CLASS_NAME,
                ConfigKeys.DOWNLOADER_CLASS_NAME,
                ConfigKeys.PIPLINE_CLASS_NAME,
                ConfigKeys.PROCESSOR_CLASS_NAME,
                ConfigKeys.CACHER_CLASS_NAME,
        };

        result = result && require(taskConfig, requiredParameters);


        /**
         * 必须有一个
         */
        String[] requireOne = {ConfigKeys.GROOVY_FILE, ConfigKeys.GROOVY_SCRIPT_CODE};
        result = result && requireOne(taskConfig, requireOne);

        return result;
    }

    /**
     * 必须的参数
     * @param cfg
     * @param allKeys
     * @return
     */
    private static boolean require(TaskConfig cfg, String[]allKeys) {
        boolean isOk = true;
        for (String configKey : allKeys) {
            boolean exists = cfg.checkValueExists(configKey);
            isOk = isOk && exists;
            if (!exists) {
                System.err.printf("需要参数:\t%s\n", configKey);
            }
        }

        return isOk;
    }

    /**
     * 多个里必须有一个
     * @param cfg
     * @param configKeys
     * @return
     */
    private static boolean requireOne(TaskConfig cfg, String[] configKeys) {
        for (String key : configKeys) {
            boolean exists = cfg.checkValueExists(key);
            if (exists) {
                return true;
            }
        }
        System.err.printf("下列参数中必须要有一个存在:\t%s\n", configKeys);
        return false;
    }

    /**
     * 依赖
     */
    private static boolean requireIfExists(TaskConfig cfg, String key, String[] require) {
        return false;
    }
}
