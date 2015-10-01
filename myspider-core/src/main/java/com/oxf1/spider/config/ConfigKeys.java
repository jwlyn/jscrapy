package com.oxf1.spider.config;

/**
 * Created by cxu on 2015/6/21.
 */
public class ConfigKeys {

    //保存本地任务的配置，缓存，数据等的总目录地址
    public static String SPIDER_WORK_DIR = "SPIDER_WORK_DIR";

    /*redis*/
    public static final String REDIS_DEDUP_SERVER = "REDIS_DEDUP_SERVER";

    /*mongodb*/
    public static final String MONGODB_HOST = "MONGODB_HOST";
    public static final String MONGODB_PORT = "MONGODB_PORT";
    public static final String MONGODB_CACHER_DB_NAME = "MONGODB_CACHER_DB_NAME";//缓存
    public static final String MONGODB_DEDUP_DB_NAME = "MONGODB_DEDUP_DB_NAME";//去重

    /*内存队列*/
    public static final String MEM_DEDUP_SET = "MEM_DEDUP_SET";
    public static final String MEM_SCHEDULER_QUEUE = "MEM_SCHEDULER_QUEUE";

    /*Groovy解析脚本对象*/
    public static final String GROOVY_PROCESSOR_OBJ = "groovy_processor_object";

    /*----------------------------------------------------------------------------------
    run time config
     在运行时组合生成的配置
    ------------------------------------------------------------------------------------
     */
    public static final String RT_LOCAL_FILE_PIPLINE_DATA_FILE = "RT_LOCAL_FILE_PIPLINE_DATA_FILE";
    public static final String RT_LOCAL_CACHER_DIR = "RT_LOCAL_CACHER_DIR";
    public static final String RT_LOCAL_QUEUE_DIR = "RT_LOCAL_QUEUE_DIR";
}
