package com.oxf1.spider.config;

import com.oxf1.spider.TaskConfig;

/**
 * Created by cxu on 2015/6/21.
 */
public interface ConfigOperator {
    public String loadString(TaskConfig taskConfig, String key);
    public Integer loadInt(TaskConfig taskConfig, String key);

    public void put(TaskConfig taskConfig, String key, Object value);
}
