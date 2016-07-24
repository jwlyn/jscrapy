package org.jscrapy.contrib.dedup;

import org.jscrapy.core.TaskConfig;
import org.jscrapy.core.config.cfgkey.ConfigKeys;
import org.jscrapy.core.dedup.DeDup;
import org.jscrapy.core.request.Request;

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
        if(taskConfig.getTaskSharedObject(ConfigKeys._DEDUP_MEM_SET_OBJ)==null){
            synchronized (taskConfig){
                if(taskConfig.getTaskSharedObject(ConfigKeys._DEDUP_MEM_SET_OBJ)==null){
                    ConcurrentHashMap<String, Character> existUrl = new ConcurrentHashMap<String, Character>(10000);
                    taskConfig.addTaskSharedObject(ConfigKeys._DEDUP_MEM_SET_OBJ, existUrl);
                }
            }
        }
    }

    @Override
    protected boolean isDup(Request request) {
        ConcurrentHashMap<String, Character> existUrl = (ConcurrentHashMap<String, Character>)getTaskConfig().getTaskSharedObject(ConfigKeys._DEDUP_MEM_SET_OBJ);
        String id = request.fp();
        Character ret = existUrl.putIfAbsent(id, '1');
        return ret!=null;
    }

    @Override
    public void close() {

    }
}
