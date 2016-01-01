package com.oxf1.myspider.config.cfgkey;

/**
 * _开头：的是一般的常量key,
 * RT_VAR开头：运行过程中会变化的量。比如任务的状态从开始(RUN)到结束(FINISH)
 * RT_EXT开头：派生出的配置。比如使用mongodb作为缓存的时候，会自动生成一个db，名字以任务的名字一样。
 *
 * Created by cxu on 2015/6/21.
 */
public class ConfigKeys {

    /////////////////////////////////////////////////////////////////////////
    public static final String TASK_ID = "task.id";
    public static final String TASK_NAME = "task.name";
    public static final String TASK_VIRTUAL_ID = "task.virtual.id";

    //保存本地任务的配置，缓存，数据等的总目录地址
    public static final String TASK_WORK_DIR = "task.work.dir";
    public static final String TASK_WAIT_URL_TIMEOUT = "task.wait.url.timeout";
    public static final String TASK_THREAD_COUNT = "task.thread.count";

    /////////////////////////////////////////////////////////////////////////
    public static final String SCHEDULER_BATCH_SIZE = "scheduler.batch.size";
    public static final String SCHEDULER_CLASS_NAME = "scheduler.class.name";
    public static final String SCHEDULER_REDIS_HOST = "scheduler.redis.host";
    public static final String SCHEDULER_MONGO_HOST = "scheduler.mongo.host";
    public static final String SCHEDULER_MONGO_PORT = "scheduler.mongo.port";

    /////////////////////////////////////////////////////////////////////////
    public static final String DEDUP_CLASS_NAME = "dedup_class_name";
    public static final String DEDUP_REDIS_HOST = "dedup.redis.host";
    public static final String DEDUP_MONGO_HOST = "dedup.mongo.host";
    public static final String DEDUP_MONGO_PORT = "dedup.mongo.port";

    /////////////////////////////////////////////////////////////////////////
    public static final String DOWNLOADER_CLASS_NAME = "downloader.class.name";

    /////////////////////////////////////////////////////////////////////////
    public static final String PIPLINE_CLASS_NAME = "pipline.class.name";

    /////////////////////////////////////////////////////////////////////////
    public static final String PROCESSOR_CLASS_NAME = "processor.class.name";

    /////////////////////////////////////////////////////////////////////////
    public static final String CACHER_CLASS_NAME  = "cacher.class.name";
    public static final String CACHER_MONGODB_HOST = "cacher.mongo.host";
    public static final String CACHER_MONGODB_PORT = "cacher.mongo.port";

    /////////////////////////////////////////////////////////////////////////
    // 发不到网络(nuxus)上的jar包(内含解析，提取链接、业务流程控制等逻辑)
    public static final String PLUGIN_URL = "plugin.url";

    /////////////////////////////////////////////////////////////////////////
    public static final String RT_EXT_DEDUP_MONGODB_DB_NAME = "rt.ext.mongodb.dedup.db.name";//去重
    public static final String RT_EXT_CACHER_MONGODB_DB_NAME = "rt.ext.mongodb.cacher.db.name";//缓存
    public static final String RT_EXT_TASK_LOGGER = "rt.ext.task.logger";
    public static final String RT_EXT_RT_LOCAL_FILE_PIPLINE_DATA_FILE = "rt.ext.local.file.pipline.data.file";
    public static final String RT_EXT_RT_LOCAL_TASK_WORK_DIR = "rt.ext.local.task.work.dir";
    public static final String RT_EXT_RT_LOCAL_TASK_CACHER_DIR = "rt.ext.local.task.cacher.dir";
    public static final String RT_EXT_RT_LOCAL_QUEUE_DIR = "rt.ext.local.queue.dir";
    public static final String RT_EXT_RT_TASK_LOG_DIR = "rt.ext.task.log.dir";//分任务记录的日志

    /////////////////////////////////////////////////////////////////////////
    /*任务当时应该处于的状态：运行、暂停(保存队列)、结束(需要清空队列)*/
    public static final String RT_VAR_TASK_CTL_CMD = "rt.var.task.control.cmd";

    /////////////////////////////////////////////////////////////////////////
    public static final String _SCHEDULER_MEM_QUEUE_OBJ = "_scheduler_mem_queue";
    public static final String _SCHEDULER_DISK_QUEUE_OBJ = "_scheduler_disk_queue";
    public static final String _DEDUP_MEM_SET_OBJ = "_dedup_mem_set_obj";
    public static final String _DEDUP_DISK_SET_OBJ = "_dedup_disk_set_obj";
    public static final String _SCHEDULER_OBJ = "_scheduler_obj";
    public static final String _TASK_STATUS_OBJ = "_task_status_obj";
    public static final String _GROOVY_PROCESSOR_OBJ = "_groovy_processor_obj";
    public static final String _GROOVY_SCRIPT_OBJ = "_groovy_script_obj";
}
