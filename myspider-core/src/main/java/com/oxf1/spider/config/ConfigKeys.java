package com.oxf1.spider.config;

/**
 * Created by cxu on 2015/6/21.
 */
public class ConfigKeys {
    //ehCache 的配置集合，爬虫配置专用
    public static String EH_CACHE_NAME = "com.oxf1.myspider.config.task";
    /*单机爬虫解析之后数据文件保存的位置*/
    public static final String LOCAL_FILE_PIPLINE_DATA_SAVE_PATH = "LOCAL_FILE_PIPLINE_DATA_SAVE_PATH";
    /*单机调度队列的磁盘位置*/
    public static final String LOCAL_SCHEDULE_QUEUE_PATH="LOCAL_SCHEDULE_QUEUE_PATH";

}
