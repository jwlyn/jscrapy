package org.jscrapy.core.config;

/**
 * Created by cxu on 2017/1/16.
 */
public enum  ComponentName {

    DEDUP_REDIS("dedup_redis"),
    DEDUP_MONGO("dedup_mongo"),
    DEDUP_H2("dedup_h2"),

    QUEUE_H2("queue_h2"),
    SCHEDULER_REDIS("scheduler_redis"),

    _END("");

    private String name;

    ComponentName(String name) {
        this.name = name;
    }

}
