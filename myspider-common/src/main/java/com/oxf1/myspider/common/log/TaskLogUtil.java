package com.oxf1.myspider.common.log;

import org.slf4j.helpers.MessageFormatter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Created by cxu on 2015/11/7.
 */
public class TaskLogUtil {

    /**
     *
     * @param logger
     * @param methodName
     * @param msgPattern
     * @param args
     */
    public static void log(Object logger, String methodName, String msgPattern, Object ... args) {
        ArrayList<Object> objs = new ArrayList<Object>();
        for (Object o : args) {
            objs.add(o);
        }
        String msg = MessageFormatter.arrayFormat(msgPattern, objs.toArray()).getMessage();

        log(logger, methodName, msg);
    }

    /**
     *
     * @param logger
     * @param methodName
     * @param msg
     */
    public static void log(Object logger, String methodName, String msg) {
        //动态调用
        Method method = null;
        try {
            method = logger.getClass().getDeclaredMethod(methodName, new Class[]{String.class});
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        try {
            method.invoke(logger, msg);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
