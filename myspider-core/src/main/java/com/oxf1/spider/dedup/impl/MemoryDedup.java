package com.oxf1.spider.dedup.impl;

import com.oxf1.spider.TaskConfig;
import com.oxf1.spider.config.ConfigKeys;
import com.oxf1.spider.dedup.DeDup;
import com.oxf1.spider.request.Request;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 使用jdk Set完成去重
 * Created by cxu on 2015/9/18.
 */
public class MemoryDedup  extends DeDup {

    /**
     * 构造函数
     * @param taskConfig
     */
    public MemoryDedup(TaskConfig taskConfig) {
        super(taskConfig);
        if(taskConfig.getTaskSharedObject(ConfigKeys.MEM_DEDUP_SET)==null){
            synchronized (taskConfig){
                if(taskConfig.getTaskSharedObject(ConfigKeys.MEM_DEDUP_SET)==null){
                    ConcurrentHashMap<String, Character> existUrl = new ConcurrentHashMap<String, Character>(10000);
                    taskConfig.addTaskSharedObject(ConfigKeys.MEM_DEDUP_SET, existUrl);
                }
            }
        }
    }

    @Override
    protected boolean isDup(Request request) {
        ConcurrentHashMap<String, Character> existUrl = (ConcurrentHashMap<String, Character>)getTaskConfig().getTaskSharedObject(ConfigKeys.MEM_DEDUP_SET);
        String id = request.fp();
        Character ret = existUrl.putIfAbsent(id, '1');
        return ret!=null;
    }

    @Override
    public void close() {

    }
}
