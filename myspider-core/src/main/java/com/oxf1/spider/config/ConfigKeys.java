package com.oxf1.spider.config;

/**
 * Created by cxu on 2015/6/21.
 */
public class ConfigKeys {

    //保存本地任务的配置，缓存，数据等的总目录地址
    public static String SPIDER_WORK_DIR = "SPIDER_WORK_DIR";
    //ehCache 的配置集合，爬虫配置专用
    public static String MYSPIER_CONFIG_NAME = "com_oxf1_myspider_task_config";
    /*单机爬虫解析之后数据文件保存的位置*/
    public static final String LOCAL_FILE_PIPLINE_DATA_SAVE_PATH = "LOCAL_FILE_PIPLINE_DATA_SAVE_PATH";
    /*单机调度队列的磁盘位置*/
    public static final String LOCAL_SCHEDULE_QUEUE_PATH="LOCAL_SCHEDULE_QUEUE_PATH";

    /*redis*/
    public static final String REDIS_DEDUP_SERVER = "REDIS_DEDUP_SERVER";

    /*mongodb*/
    public static final String MONGODB_HOST = "MONGODB_HOST";
    public static final String MONGODB_PORT = "MONGODB_PORT";
    public static final String MONGODB_CACHER_DB_NAME = "MONGODB_CACHER_DB_NAME";//缓存
    public static final String MONGODB_DEDUP_DB_NAME = "MONGODB_DEDUP_DB_NAME";//去重

    public static final String MEM_DEDUP_MAP = "MEM_DEDUP_MAP";
    public static final String MEM_SCHEDULER_MAP = "MEM_SCHEDULER_MAP";

    /*----------------------------------------------------------------------------------
    run time config
     在运行时组合生成的配置
    ------------------------------------------------------------------------------------
     */
    public static final String RT_LOCAL_FILE_PIPLINE_DATA_FILE = "RT_LOCAL_FILE_PIPLINE_DATA_FILE";
    public static final String RT_LOCAL_CACHER_DIR = "RT_LOCAL_CACHER_DIR";
}
