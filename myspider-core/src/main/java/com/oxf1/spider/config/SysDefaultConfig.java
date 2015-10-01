package com.oxf1.spider.config;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 系统常量
 * Created by cxu on 2015/9/18.
 */
public class SysDefaultConfig {
    public static final String  DEFAULT_SPIDER_WORK_DIR = System.getProperty("user.home")
            + File.separator
            + ".myspider"
            + File.separator;

    public static String HOST;//本机的IP
    public static String VIRTUAL_ID = ManagementFactory.getRuntimeMXBean().getName();;//JVM进程号，虚拟化本机任务使用
    public static int SCHEDULER_BATCH_SIZE = 10;//默认每次从队列里拿出来多少url

    static{
        InetAddress addr = null;
        try {
            addr = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            HOST = "?.?.?.?";
            //TODO log error
        }
        if(StringUtils.isBlank(HOST)){
            HOST = addr.getHostAddress().toString();
        }
    }


}
