package com.oxf1.spider;

import com.oxf1.spider.config.ConfigOperator;
import com.oxf1.spider.config.SysDefaultConfig;
import com.oxf1.spider.config.impl.YamlConfigOperator;

import java.io.File;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by cxu on 2015/6/21.
 */
public class TaskConfig{

    private final String taskId;
    private final String taskName;
    private final ConfigOperator cfg;
    /**
     * 存放同一个任务需要的多线程共享对象
     */
    private ConcurrentHashMap<String, Object> taskSharedObject;


    public TaskConfig(String taskId, String taskName){
        this.taskId = taskId;
        this.taskName = taskName;

        String taskConfigFile = SysDefaultConfig.DEFAULT_SPIDER_WORK_DIR + getTaskFp() + File.separator + "cfg.yaml";
        this.cfg = new YamlConfigOperator(taskConfigFile);//默认的
        this.taskSharedObject = new ConcurrentHashMap<String, Object>(5);
    }

    public String getTaskId() {
        return this.taskId;
    }

    public String getTaskName(){
        return this.taskName;
    }

    public String loadString(String key) {
        Object value = this.cfg.loadValue(key);
        if(value!=null){
            return (String)value;
        }
        else return null;
    }

    /**
     * 从配置读出一个key,转化为int
     * @param key
     * @return
     */
    public Integer loadInt(String key) {
        Object value =  this.cfg.loadValue(key);
        if(value!=null){
            return (Integer)value;
        }
        return null;
    }

    /**
     * 保存配置
     * @param key
     * @param value
     */
    public void put(String key, Object value) {
        cfg.put(key, value);
    }

    public void addTaskSharedObject(String key, Object obj){
        taskSharedObject.put(key, obj);
    }

    public Object getTaskSharedObject(String key){
        Object o = taskSharedObject.get(key);
        return o;
    }

    /**
     * 本机唯一任务标示
     * @return
     */
    public String getTaskFp(){
        StringBuffer buf = new StringBuffer(10);
        buf.append(SysDefaultConfig.HOST)
                .append("@")
                .append(this.taskName)
                .append("@")
                .append(this.taskId)
                .append("@")
                .append(SysDefaultConfig.VIRTUAL_ID);//使用jvm进程Id可以在一台机器上模拟分布式

        return buf.toString();
    }
}
