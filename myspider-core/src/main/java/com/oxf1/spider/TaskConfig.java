package com.oxf1.spider;

import com.oxf1.spider.config.ConfigKeys;
import com.oxf1.spider.config.ConfigOperator;
import com.oxf1.spider.config.SysDefaultConfig;
import com.oxf1.spider.config.impl.YamlConfigOperator;
import com.oxf1.spider.scheduler.Scheduler;
import com.oxf1.spider.status.TaskStatus;
import groovy.lang.GroovyObject;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
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

        cfg.put(ConfigKeys.TASK_ID, taskId);
        cfg.put(ConfigKeys.TASK_NAME, taskName);
    }

    /**
     * 从yaml文件load一个任务
     * @param taskConfigFile
     */
    public TaskConfig(String taskConfigFile) throws IOException {
        this.cfg = new YamlConfigOperator(taskConfigFile);
        this.cfg.reload();
        this.taskSharedObject = new ConcurrentHashMap<String, Object>(5);

        taskId = loadString(ConfigKeys.TASK_ID);
        taskName = loadString(ConfigKeys.TASK_NAME);
    }

    public String getTaskId() {
        return loadString(ConfigKeys.TASK_ID);
    }

    public String getTaskName() {
        return loadString(ConfigKeys.TASK_NAME);
    }

    /**
     * scheduler每次从队列里取出的请求数目, 默认1个
     * @return
     */
    public int getSchedulerBatchSize() {
        Integer size =  loadInt(ConfigKeys.SCHEDULER_BATCH_SIZE);
        if (size == null) {
            size = SysDefaultConfig.SCHEDULER_BATCH_SIZE;
        }
        return size;
    }

    /**
     * 每个任务开启的线程数目,默认是1个
     * @return
     */
    public int getThreadCount() {
        Integer threadCount = loadInt(ConfigKeys.THREAD_COUNT);
        if (threadCount == null) {
            threadCount = SysDefaultConfig.THREAD_COUNT;
        }
        return threadCount;
    }

    /**
     * 爬虫模块的class名字
     */
    public String getSchedulerClassName() {
        return loadString(ConfigKeys.SCHEDULER_CLASS_NAME);
    }

    public String getDedupClassName() {
        return loadString(ConfigKeys.DEDUP_CLASS_NAME);
    }

    public String getDownloaderClassName() {
        return loadString(ConfigKeys.DOWNLOADER_CLASS_NAME);
    }

    public String getPiplineClassName() {
        return loadString(ConfigKeys.PIPLINE_CLASS_NAME);
    }

    public String getProcessorClassName() {
        return loadString(ConfigKeys.PROCESSOR_CLASS_NAME);
    }

    public String getCacherClassName() {
        return loadString(ConfigKeys.CACHER_CLASS_NAME);
    }

    /**
     * 工作目录，放配置，缓存等的目录位置
     * @return
     */
    public String getTaskWorkDir() {
        String workDir = loadString(ConfigKeys.SPIDER_WORK_DIR);
        if (StringUtils.isBlank(workDir)) {
            workDir = SysDefaultConfig.DEFAULT_SPIDER_WORK_DIR;
        }
        return workDir;
    }

    public String getTaskStatus() {
        return loadString(ConfigKeys.TASK_STATUS);
    }

    public void setTaskStatus(TaskStatus.Status status) {
        put(ConfigKeys.TASK_STATUS, status.name());
    }

    /**
     * 队列空的时候，睡眠等待时间
     * @return
     */
    public int getWaitUrlSleepTimeMs() {
        Integer waitMs = loadInt(ConfigKeys.WAIT_URL_SLEEP_TIME_MS);
        if (waitMs == null) {
            waitMs = SysDefaultConfig.WAIT_URL_SLEEP_TIME_MS;
        }
        return waitMs;
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

    public GroovyObject getGroovyProcessor() {
        return (GroovyObject)this.getTaskSharedObject(ConfigKeys.GROOVY_PROCESSOR_OBJ);
    }

    public Scheduler getScheduler() {
        return (Scheduler)this.getTaskSharedObject(ConfigKeys.SCHEDULER);
    }

    public void setScheduler(Scheduler scheduler) {
        taskSharedObject.put(ConfigKeys.SCHEDULER, scheduler);
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
