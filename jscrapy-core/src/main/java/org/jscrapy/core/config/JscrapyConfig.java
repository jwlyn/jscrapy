package org.jscrapy.core.config;

import org.jscrapy.core.config.modulecfg.TaskBaseConfig;
import org.jscrapy.core.config.modulecfg.TaskComponentConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cxu on 2015/6/21.
 */
public class JscrapyConfig {
    final static Logger logger = LoggerFactory.getLogger(JscrapyConfig.class);

    public TaskBaseConfig getTaskBaseConfig() {
        return taskBaseConfig;
    }

    private TaskBaseConfig taskBaseConfig;//基本任务配置
    private Map<ComponentName, TaskComponentConfig> taskComponentConfigs;//基本组件配置

    public JscrapyConfig() {
        taskComponentConfigs = new HashMap<>();
    }

    public void setTaskBaseConfig(TaskBaseConfig taskBaseConfig) {
        this.taskBaseConfig = taskBaseConfig;
    }

    public void setTaskComponentConfig(ComponentName key, TaskComponentConfig taskComponentConfig) {
        taskComponentConfigs.put(key, taskComponentConfig);
    }

    public Map<ComponentName, TaskComponentConfig> getTaskComponentConfigs() {
        return taskComponentConfigs;
    }

    public void setTaskComponentConfigs(Map<ComponentName, TaskComponentConfig> taskComponentConfigs) {
        this.taskComponentConfigs = taskComponentConfigs;
    }

    public TaskComponentConfig get(ComponentName componentName) {
        return taskComponentConfigs.get(componentName);
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

    /**
     * 工作目录，放配置，缓存等的目录位置
     *
     * @return
     */
    public static String getSpiderWorkDir() {
        return SysDefaultConfig.DEFAULT_SPIDER_WORK_DIR;
    }

    public String getTaskWorkDir() {
        String taskWorkDir = getSpiderWorkDir() + getTaskFp() + File.separator;
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
