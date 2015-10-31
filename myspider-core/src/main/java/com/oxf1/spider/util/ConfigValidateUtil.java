package com.oxf1.spider.util;

import com.oxf1.spider.TaskConfig;
import com.oxf1.spider.config.ConfigKeys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 从配置文件加载一个任务之前利用这个类校验参数的完备性和合法性
 *
 * Created by cxu on 2015/10/3.
 */
public class ConfigValidateUtil {
    final static Logger logger = LoggerFactory.getLogger(ConfigValidateUtil.class);
    public static boolean validate(TaskConfig taskConfig, StringBuffer errorMsg) {
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
                ConfigKeys.GROOVY_FILE,
        };

        result = result && require(taskConfig, requiredParameters, errorMsg);

        return result;
    }

    /**
     * 必须的参数
     * @param cfg
     * @param allKeys
     * @return
     */
    private static boolean require(TaskConfig cfg, String[]allKeys, StringBuffer errorMsg) {
        boolean isOk = true;
        for (String configKey : allKeys) {
            boolean exists = cfg.checkValueExists(configKey);
            isOk = isOk && exists;
            if (!exists) {
                logger.error("缺少必须参数: {}", configKey);
                errorMsg.append("缺少必须参数：").append(configKey).append("\n");
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
        logger.error("下列参数中必须要有一个存在: {}", configKeys);
        return false;
    }

    /**
     * 依赖
     */
    private static boolean requireIfExists(TaskConfig cfg, String key, String[] require) {
        return false;
    }
}
