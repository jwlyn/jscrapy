package com.oxf1.spider;

import com.oxf1.spider.config.ConfigKeys;
import com.oxf1.spider.config.ConfigOperator;
import com.oxf1.spider.config.SysDefaultConfig;
import com.oxf1.spider.config.impl.YamlConfigOperator;
import com.oxf1.spider.config.util.ConfigPrepareUtil;
import com.oxf1.spider.exception.MySpiderExceptionCode;
import com.oxf1.spider.exception.MySpiderFetalException;
import com.oxf1.spider.scheduler.Scheduler;
import com.oxf1.spider.status.TaskStatus;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import groovy.lang.Script;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by cxu on 2015/6/21.
 */
public class TaskConfig {
    final static Logger logger = LoggerFactory.getLogger(TaskConfig.class);
    private final ConfigOperator cfg;
    /**
     * 存放同一个任务需要的多线程共享对象
     */
    private ConcurrentHashMap<String, Object> taskSharedObject;

    /**
     * 从yaml文件load一个任务
     *
     * @param taskConfigFile
     */
    public TaskConfig(String taskConfigFile) throws MySpiderFetalException {
        this.cfg = new YamlConfigOperator(taskConfigFile);
        this.taskSharedObject = new ConcurrentHashMap<String, Object>(5);
        String taskConfigFilePath = getTaskConfigFilePath();
        cfg.rebaseConfigDir(taskConfigFilePath);

        initTaskStatusObject();
        try {
            String groovyCode = null;
            String groovyFile = loadString(ConfigKeys.GROOVY_FILE);
            if (groovyFile != null) {
                File f = new File(groovyFile);
                if (f.exists() && !f.isDirectory()) {//绝对路径
                    groovyCode = FileUtils.readFileToString(f);
                } else {//相对路径？
                    String path = FilenameUtils.getFullPath(taskConfigFile);
                    path = path + groovyFile;
                    f = new File(path);
                    if (f.exists() && !f.isDirectory()) {
                        groovyCode = FileUtils.readFileToString(f);
                    }
                }
                if (StringUtils.isNotBlank(groovyCode)) {
                    setGroovyScript(groovyCode);
                } else {
                    logger.error("groovy file {} is empty", groovyFile);
                }
            } else {
                logger.error("groovy file {} not exists", groovyFile);
            }
        } catch (IOException e) {
            logger.error("加载Groovy文件失败 {}", e);
            MySpiderFetalException exp = new MySpiderFetalException(MySpiderExceptionCode.GROOVY_PARSER_NOT_FOUND);
            exp.setErrorMessage(e.getLocalizedMessage());
            throw exp;
        }
        ConfigPrepareUtil.prepareConfig(this);
    }

    public void setGroovyScript(String groovyCode) {
        if (StringUtils.isNotBlank(groovyCode)) {
            GroovyObject o = instanceClass(groovyCode);
            taskSharedObject.put(ConfigKeys.RT_GROOVY_SCRIPT_OBJECT, o);
        } else {
            logger.error("groovyCode is empty!");
        }

    }

    public String getTaskId() {
        return loadString(ConfigKeys.TASK_ID);
    }

    public String getTaskName() {
        return loadString(ConfigKeys.TASK_NAME);
    }

    /**
     * 每个任务分别记录日志的路径
     * @return
     */
    public String getTaskLogDir() {
        return loadString(ConfigKeys.RT_TASK_LOG_DIR);
    }

    /**
     * scheduler每次从队列里取出的请求数目, 默认1个
     *
     * @return
     */
    public int getSchedulerBatchSize() {
        Integer size = loadInt(ConfigKeys.SCHEDULER_BATCH_SIZE);
        if (size == null) {
            size = SysDefaultConfig.SCHEDULER_BATCH_SIZE;
        }
        return size;
    }

    /**
     * 每个任务开启的线程数目,默认是1个
     *
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

    public String getVirtualId() {
        String virtualId = null;
        virtualId = loadString(ConfigKeys.VIRTUAL_ID);

        if (StringUtils.isBlank(virtualId)) {
            virtualId = SysDefaultConfig.VIRTUAL_ID;
        }
        return virtualId;
    }

    /**
     * 工作目录，放配置，缓存等的目录位置
     *
     * @return
     */
    public String getSpiderWorkDir() {
        String workDir = loadString(ConfigKeys.SPIDER_WORK_DIR);
        if (StringUtils.isBlank(workDir)) {
            workDir = SysDefaultConfig.DEFAULT_SPIDER_WORK_DIR;
        }
        if(!workDir.endsWith(File.separator)){
            workDir = workDir + File.separator;
        }
        return workDir;
    }

    public String getTaskWorkDir() {
        String taskWorkDir = loadString(ConfigKeys.RT_LOCAL_TASK_WORK_DIR);
        return taskWorkDir;
    }

    public String getTaskCacheDir() {
        String taskCacheDir = loadString(ConfigKeys.RT_LOCAL_TASK_CACHER_DIR);
        return taskCacheDir;
    }

    public String getTaskStatus() {
        return loadString(ConfigKeys.TASK_STATUS);
    }

    public void setTaskStatus(TaskStatus.Status status) throws MySpiderFetalException {
        put(ConfigKeys.TASK_STATUS, status.name());
    }

    /**
     * 队列空的时候，睡眠等待时间
     *
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
        if (value != null) {
            return (String) value;
        } else return null;
    }

    /**
     * 检查某一个key对应的值是不是存在的
     *
     * @param key
     * @return
     */
    public boolean checkValueExists(String key) {
        Object value1 = this.cfg.loadValue(key);
        Object value2 = getTaskSharedObject(key);
        return (value1 != null || value2 != null);
    }

    /**
     * 从配置读出一个key,转化为int
     *
     * @param key
     * @return
     */
    public Integer loadInt(String key) {
        Object value = this.cfg.loadValue(key);
        if (value != null) {
            return (Integer) value;
        }
        return null;
    }

    /**
     * 保存配置
     *
     * @param key
     * @param value
     */
    public void put(String key, Object value) throws MySpiderFetalException {
        cfg.put(key, value);
    }

    public GroovyObject getGroovyProcessorObject() {
        return (GroovyObject) this.getTaskSharedObject(ConfigKeys.RT_GROOVY_PROCESSOR_OBJECT);
    }

    /**
     * 每个任务的多个线程共用一个scheduler对象。防止竞争和去重不干净问题。
     *
     * @return
     */
    public Scheduler getSchedulerObject() {
        return (Scheduler) this.getTaskSharedObject(ConfigKeys.RT_SCHEDULER_OBJECT);
    }

    /**
     * 由Task初始化时调用,之后每个spider对象都共用这个Scheduler对象
     *
     * @param scheduler
     */
    public void setSchedulerObject(Scheduler scheduler) {
        taskSharedObject.put(ConfigKeys.RT_SCHEDULER_OBJECT, scheduler);
    }

    /**
     * 任务级别的日志
     * @param logger
     */
    public void setTaskLogger(org.apache.log4j.Logger logger) {
        taskSharedObject.put(ConfigKeys.TASK_LOGGER, logger);
    }

    public org.apache.log4j.Logger getTaskLogger() {
        return (org.apache.log4j.Logger)taskSharedObject.get(ConfigKeys.TASK_LOGGER);
    }

    public void addTaskSharedObject(String key, Object obj) {
        taskSharedObject.put(key, obj);
    }

    public void initTaskStatusObject() {
        TaskStatus status = new TaskStatus();
        taskSharedObject.put(ConfigKeys.RT_TASK_STATUS_OBJECT, status);
    }

    public TaskStatus getTaskStatusObject() {
        TaskStatus status = (TaskStatus) taskSharedObject.get(ConfigKeys.RT_TASK_STATUS_OBJECT);
        return status;
    }

    public Object getTaskSharedObject(String key) {
        Object o = taskSharedObject.get(key);
        return o;
    }

    /**
     * 本机唯一任务标示
     *
     * @return
     */
    public String getTaskFp() {
        String taskId = loadString(ConfigKeys.TASK_ID);
        String taskName = loadString(ConfigKeys.TASK_NAME);
        StringBuffer buf = new StringBuffer(10);
        buf.append(SysDefaultConfig.HOST)
                .append("@")
                .append(taskName)
                .append("@")
                .append(taskId)
                .append("@")
                .append(getVirtualId());//使用jvm进程Id可以在一台机器上模拟分布式

        return buf.toString();
    }

    /**
     * 从Groovy脚本实例化对象
     *
     * @param scriptCode
     * @return
     */
    private GroovyObject instanceClass(String scriptCode) {
        Class<Script> klass = null;
        GroovyObject parser = null;
        try {
            klass = new GroovyClassLoader().parseClass(scriptCode);
        } catch (Exception e) {
            logger.error("groovyCode编译异常{}", e);

        }

        try {
            if (klass == null) {
                throw new Exception("ERROR ## 脚本加载异常.");
            } else {
                parser = (GroovyObject) klass.newInstance();
            }
        } catch (Exception e) {//经过校验之后，这里是很可能不发生的
            logger.error("groovy脚本实例化异常{}", e);
        }

        return parser;
    }

    /**
     *
     * @return
     */
    private String getTaskConfigFilePath(){
        return getSpiderWorkDir() + getTaskFp() + File.separator + "task.yaml";
    }
}
