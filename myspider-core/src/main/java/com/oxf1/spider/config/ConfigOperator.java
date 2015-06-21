package com.oxf1.spider.config;

import com.oxf1.spider.TaskId;

/**
 * Created by cxu on 2015/6/21.
 */
public interface ConfigOperator {
    public String loadString(TaskId taskid, String key);
    public Integer loadInt(TaskId taskid, String key);

    public void put(TaskId taskid, String key, Object value);
}
