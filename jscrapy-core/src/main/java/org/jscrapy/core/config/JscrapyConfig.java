package org.jscrapy.core.config;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by cxu on 2015/6/21.
 */
public class JscrapyConfig {
    final static Logger logger = LoggerFactory.getLogger(JscrapyConfig.class);

    private String taskWorkDir = SysDefaultConfig.DEFAULT_SPIDER_WORK_DIR;
    private String taskId;//任务id,元数据里的唯一标示
    private String taskName;//任务名称
    private int urlFetchFromQueueSize;//任务每次从集中队列里取出多少个URL
    private int threadCount;//每个节点上并发的线程数目
    private int waitOnQueueEmptyMs;//当队列空的时候睡眠等待多少毫秒

    private String virtualId;//虚拟的ID，用于集群分组，伪分布式

    public void setTaskWorkDir(String taskWorkDir) {
        this.taskWorkDir = taskWorkDir;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public int getUrlFetchFromQueueSize() {
        return urlFetchFromQueueSize;
    }

    public void setUrlFetchFromQueueSize(int urlFetchFromQueueSize) {
        this.urlFetchFromQueueSize = urlFetchFromQueueSize;
    }

    public int getThreadCount() {
        return threadCount;
    }

    public void setThreadCount(int threadCount) {
        this.threadCount = threadCount;
    }

    public int getWaitOnQueueEmptyMs() {
        return waitOnQueueEmptyMs;
    }

    public void setWaitOnQueueEmptyMs(int waitOnQueueEmptyMs) {
        this.waitOnQueueEmptyMs = waitOnQueueEmptyMs;
    }

    public void setVirtualId(String virtualId) {
        this.virtualId = virtualId;
    }

    public String getVirtualId() {
        if (StringUtils.isBlank(virtualId)) {
            virtualId = SysDefaultConfig.VIRTUAL_ID;
        }
        return virtualId;
    }

    /**
     * 本机唯一任务标示
     *
     * @return
     */
    public String getTaskFp() {
        StringBuffer buf = new StringBuffer(50);
        buf.append("_")
                .append(formatIp(SysDefaultConfig.HOST))
                .append("_")
                .append(getVirtualId())//使用jvm进程Id可以在一台机器上模拟分布式
                .append("_")
                .append(getTaskName())
                .append("_")
                .append(getTaskId());

        return buf.toString();
    }

    /**
     * 将ip中的特殊符号替换成下划线
     */
    private String formatIp(String ip) {
        return StringUtils.replace(ip, ".", "_");
    }

//    /**
//     * 从yaml文件load一个任务
//     *
//     * @param taskConfigFile
//     */
//    public JscrapyConfig(String taskConfigFile) throws MySpiderFetalException {
//
//        try {
//            String groovyCode = null;
//            String groovyFile = loadString(ConfigKeys.PLUGIN_URL);
//            if (groovyFile != null) {
//                File f = new File(groovyFile);
//                if (f.exists() && !f.isDirectory()) {//绝对路径
//                    groovyCode = FileUtils.readFileToString(f);
//                } else {//相对路径？
//                    String path = FilenameUtils.getFullPath(taskConfigFile);
//                    path = path + groovyFile;
//                    f = new File(path);
//                    if (f.exists() && !f.isDirectory()) {
//                        groovyCode = FileUtils.readFileToString(f);
//                    }
//                }
//                if (StringUtils.isNotBlank(groovyCode)) {
//                    setGroovyScript(groovyCode);
//                } else {
//                    logger.error("groovy file {} is empty", groovyFile);
//                }
//            } else {
//                logger.error("groovy file {} not exists", groovyFile);
//            }
//        } catch (IOException e) {
//            logger.error("加载Groovy文件失败 {}", e);
//            MySpiderFetalException exp = new MySpiderFetalException(MySpiderExceptionCode.GROOVY_PARSER_NOT_FOUND);
//            exp.setErrorMessage(e.getLocalizedMessage());
//            throw exp;
//        }
//    }


    public String getMongoCacheHost() {
        return getString(ConfigKeys.CACHER_MONGODB_HOST);
    }

    public int getMongoCachePort() {
        return getInt(ConfigKeys.CACHER_MONGODB_PORT);
    }

    public String getMongoCacheDbName() {
        return getAppName() + "_cache";
    }

    public String getMongoCacheTableName() {
        return getTaskName();
    }

    /////////////////////////////////////////////////////////////////////////////

    /**
     *
     */
    public String getSchedulerMongoHost() {
        return getString(ConfigKeys.SCHEDULER_MONGO_HOST);
    }

    public int getSchedulerMongoPort() {
        return getInt(ConfigKeys.SCHEDULER_MONGO_PORT);
    }

    public String getSchedulerMongoDbName() {
        return getString(ConfigKeys.RT_EXT_DEDUP_MONGODB_DB_NAME);
    }

    public String getSchedulerMongoTableName() {
        return getTaskName();
    }

    /////////////////////////////////////////////////////////////////////////////

    /**
     * @param key
     * @return
     */
    private String getString(String key) {
        //TODO
        return "";
    }

    /**
     * @param key
     * @return
     */
    private int getInt(String key) {
        String value = getString(key);
        int intVal = Integer.parseInt(value);
        return intVal;
    }

    private String getAppName() {
        return SysDefaultConfig.APP_NAME;
    }

    /**
     * 工作目录，放配置，缓存等的目录位置
     *
     * @return
     */
    private static String getJscrapyWorkDir() {
        return SysDefaultConfig.DEFAULT_SPIDER_WORK_DIR;
    }

    public String getTaskWorkDir() {
        String taskWorkDir = getJscrapyWorkDir() + getTaskFp() + SysDefaultConfig.FILE_PATH_SEPERATOR;
        return taskWorkDir;
    }

}
