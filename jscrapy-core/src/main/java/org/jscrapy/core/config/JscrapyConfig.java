package org.jscrapy.core.config;

import org.apache.commons.lang3.StringUtils;
import org.jscrapy.core.config.modulecfg.TaskBaseConfig;
import org.jscrapy.core.config.modulecfg.TaskComponentConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Created by cxu on 2015/6/21.
 */
public class JscrapyConfig {
    final static Logger logger = LoggerFactory.getLogger(JscrapyConfig.class);

    private TaskBaseConfig taskBaseConfig;//基本任务配置
    private TaskComponentConfig taskComponentConfig;//基本组件配置

    public void setTaskBaseConfig(TaskBaseConfig taskBaseConfig) {
        this.taskBaseConfig = taskBaseConfig;
    }

    public void setTaskComponentConfig(TaskComponentConfig taskComponentConfig) {
        this.taskComponentConfig = taskComponentConfig;
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

    public String getTaskId() {
        return taskBaseConfig.getTaskId();
    }

    public String getTaskName() {
        return taskBaseConfig.getTaskName();
    }

    public int getThreadCount() {
        return taskBaseConfig.getThreadCount();
    }
    ////////////////////////////////////////////////////////////////

    public String getTaskFp() {
        return taskBaseConfig.getTaskFp();
    }

    /**
     * @return
     */
    public String getDedupMongoHost() {
        return getString(ConfigKeys.DEDUP_MONGO_HOST);
    }

    /**
     * @return
     */
    public int getDedupMongoPort() {
        return getInt(ConfigKeys.DEDUP_MONGO_PORT);
    }

    /**
     * @return
     */
    public String getDedupMongoDbName() {
        return getAppName() + "_dedup";
    }

    /**
     * 用于去重的数据库名字
     *
     * @return
     */
    public String getDedupMongoTableName() {
        return getTaskName();
    }
    /////////////////////////////////////////////////////////////////////////////

    /**
     * @return
     */
    public String getRedisSchedulerHost() {
        return getString(ConfigKeys.SCHEDULER_REDIS_HOST);
    }

    public String getRedisDedupHost() {
        return getString(ConfigKeys.DEDUP_REDIS_HOST);
    }

    /////////////////////////////////////////////////////////////////////////////
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

//    public void setGroovyScript(String groovyCode) {
//        if (StringUtils.isNotBlank(groovyCode)) {
//            GroovyObject o = instanceClass(groovyCode);
//            taskSharedObject.put(ConfigKeys._GROOVY_SCRIPT_OBJ, o);
//        } else {
//            logger.error("groovyCode is empty!");
//        }
//    }

//    /**
//     * 每个任务分别记录日志的路径
//     *
//     * @return
//     */
//    public String getTaskLogDir() {
//        return loadString(ConfigKeys.RT_EXT_RT_TASK_LOG_DIR);
//    }

    /**
     * 工作目录，放配置，缓存等的目录位置
     *
     * @return
     */
    public String getSpiderWorkDir() {
        String workDir = getString(ConfigKeys.TASK_WORK_DIR);
        if (StringUtils.isBlank(workDir)) {
            workDir = SysDefaultConfig.DEFAULT_SPIDER_WORK_DIR;
        }
        if (!workDir.endsWith(File.separator)) {
            workDir = workDir + File.separator;
        }
        return workDir;
    }

    public String getTaskWorkDir() {
        String taskWorkDir = getString(ConfigKeys.RT_EXT_RT_LOCAL_TASK_WORK_DIR);
        return taskWorkDir;
    }

    public String getTaskCacheDir() {
        String taskCacheDir = getString(ConfigKeys.RT_EXT_RT_LOCAL_TASK_CACHER_DIR);
        return taskCacheDir;
    }

//    /**
//     * 从Groovy脚本实例化对象
//     *
//     * @param scriptCode
//     * @return
//     */
//    private GroovyObject instanceClass(String scriptCode) {
//        Class<Script> klass = null;
//        GroovyObject parser = null;
//        try {
//            klass = new GroovyClassLoader().parseClass(scriptCode);
//        } catch (Exception e) {
//            logger.error("groovyCode编译异常{}", e);
//
//        }
//
//        try {
//            if (klass == null) {
//                throw new Exception("ERROR ## 脚本加载异常.");
//            } else {
//                parser = (GroovyObject) klass.newInstance();
//            }
//        } catch (Exception e) {//经过校验之后，这里是很可能不发生的
//            logger.error("groovy脚本实例化异常{}", e);
//        }
//
//        return parser;
//    }
}
