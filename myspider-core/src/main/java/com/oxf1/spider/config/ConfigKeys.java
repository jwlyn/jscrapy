package com.oxf1.spider.config;

/**
 * Created by cxu on 2015/6/21.
 */
public class ConfigKeys {

    public static final String TASK_ID = "task_id";
    public static final String TASK_NAME = "task_name";
    public static final String VIRTUAL_ID = "virtual_id";

    //保存本地任务的配置，缓存，数据等的总目录地址
    public static String SPIDER_WORK_DIR = "spider_work_dir";

    /*redis*/
    public static final String REDIS_DEDUP_SERVER = "redis_dedup_server";

    /*mongodb*/
    public static final String MONGODB_HOST = "mongodb_host";
    public static final String MONGODB_PORT = "mongodb_port";
    public static final String MONGODB_CACHER_DB_NAME = "mongodb_cacher_db_name";//缓存
    public static final String MONGODB_DEDUP_DB_NAME = "mongodb_dedup_db_name";//去重

    /*内存队列*/
    public static final String MEM_DEDUP_SET = "memory_dedup_set";
    public static final String MEM_SCHEDULER_QUEUE = "memory_scheduler_queue";

    /*任务当时应该处于的状态：运行、暂停(保存队列)、结束(需要清空队列)*/
    public static final String TASK_STATUS = "task_status";
    public static final String WAIT_URL_SLEEP_TIME_MS = "wait_url_sleep_time_ms";

    /*Groovy解析脚本对象*/
    public static final String GROOVY_PROCESSOR_OBJ = "groovy_processor_object";
    public static final String SCHEDULER_OBJECT = "scheduler";
    public static final String SCHEDULER_BATCH_SIZE = "scheduler_batch_size";
    public static final String THREAD_COUNT = "task_thread_count";
    public static final String TASK_STATUS_OBJ = "task_status_object";
    public static final String GROOVY_SCRIPT_CODE = "groovy_script_code";
    public static final String GROOVY_FILE = "groovy_file";
    public static final String GROOVY_SCRIPT_OBJECT = "groovy_script_object";

    /*模块类名*/
    public static final String SCHEDULER_CLASS_NAME = "scheduler_class_name";
    public static final String DEDUP_CLASS_NAME = "dedup_class_name";
    public static final String DOWNLOADER_CLASS_NAME = "downloader_class_name";
    public static final String PIPLINE_CLASS_NAME = "pipline_class_name";
    public static final String PROCESSOR_CLASS_NAME = "processor_class_name";
    public static final String CACHER_CLASS_NAME = "cacher_class_name";

    /*----------------------------------------------------------------------------------
    run time config
     在运行时组合生成的配置
    ------------------------------------------------------------------------------------
     */
    public static final String RT_LOCAL_FILE_PIPLINE_DATA_FILE = "rt_local_file_pipline_data_file";
    public static final String RT_LOCAL_CACHER_DIR = "rt_local_cacher_dir";
    public static final String RT_LOCAL_QUEUE_DIR = "rt_local_queue_dir";
}
