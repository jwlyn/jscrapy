package com.oxf1.spider.util;

import com.oxf1.spider.TaskConfig;
import com.oxf1.spider.cacher.Cacher;
import com.oxf1.spider.dedup.DeDup;
import com.oxf1.spider.downloader.Downloader;
import com.oxf1.spider.pipline.Pipline;
import com.oxf1.spider.processor.Processor;
import com.oxf1.spider.scheduler.Scheduler;

import java.lang.reflect.Constructor;

/**
 * Created by cxu on 2015/10/2.
 */
public class ClassLoadUtil {

    public static Scheduler loadScheduler(String className, TaskConfig arg) {
        Object o = loadClass(className, arg);
        if (o != null) {
            return (Scheduler)o;
        }
        else{
            return null;
        }
    }

    public static DeDup loadDedup(String className, TaskConfig arg) {
        Object o = loadClass(className, arg);
        if (o != null) {
            return (DeDup)o;
        }
        else{
            return null;
        }
    }

    public static Downloader loadDownloader(String className, TaskConfig arg) {
        Object o = loadClass(className, arg);
        if (o != null) {
            return (Downloader)o;
        }
        else{
            return null;
        }
    }

    public static Processor loadProcessor(String className, TaskConfig arg) {
        Object o = loadClass(className, arg);
        if (o != null) {
            return (Processor)o;
        }
        else{
            return null;
        }
    }

    public static Cacher loadCacher(String className, TaskConfig arg) {
        Object o = loadClass(className, arg);
        if (o != null) {
            return (Cacher)o;
        }
        else{
            return null;
        }
    }

    public static Pipline loadPipline(String className, TaskConfig arg) {
        Object o = loadClass(className, arg);
        if (o != null) {
            return (Pipline)o;
        }
        else{
            return null;
        }
    }

    private static Object loadClass(String className, TaskConfig arg) {
        Object o = null;
        try {
            Class c = Class.forName(className);
            Constructor constructor = c.getConstructor(new Class[]{TaskConfig.class});
            o = constructor.newInstance(arg);
        } catch (Exception e) {
            //TODO log it
        }

        return o;
    }

}
