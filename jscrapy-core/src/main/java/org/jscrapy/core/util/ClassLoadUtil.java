package org.jscrapy.core.util;

import org.jscrapy.core.config.JscrapyConfig;
import org.jscrapy.core.cacher.Cacher;
import org.jscrapy.core.downloader.Downloader;
import org.jscrapy.core.exception.MySpiderExceptionCode;
import org.jscrapy.core.processor.Processor;
import org.jscrapy.core.dedup.DeDup;
import org.jscrapy.core.exception.MySpiderFetalException;
import org.jscrapy.core.pipline.Pipline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.MessageFormatter;

import java.lang.reflect.Constructor;

/**
 * Created by cxu on 2015/10/2.
 */
public class ClassLoadUtil {
    final static Logger logger = LoggerFactory.getLogger(ClassLoadUtil.class);



    public static DeDup loadDedup(String className, JscrapyConfig arg) throws MySpiderFetalException {
        Object o = loadClass(className, arg);
        if (o != null) {
            return (DeDup)o;
        }
        else{
            return null;
        }
    }

    public static Downloader loadDownloader(String className, JscrapyConfig arg) throws MySpiderFetalException {
        Object o = loadClass(className, arg);
        if (o != null) {
            return (Downloader)o;
        }
        else{
            return null;
        }
    }

    public static Processor loadProcessor(String className, JscrapyConfig arg) throws MySpiderFetalException {
        Object o = loadClass(className, arg);
        if (o != null) {
            return (Processor)o;
        }
        else{
            return null;
        }
    }

    public static Cacher loadCacher(String className, JscrapyConfig arg) throws MySpiderFetalException {
        Object o = loadClass(className, arg);
        if (o != null) {
            return (Cacher)o;
        }
        else{
            return null;
        }
    }

    public static Pipline loadPipline(String className, JscrapyConfig arg) throws MySpiderFetalException {
        Object o = loadClass(className, arg);
        if (o != null) {
            return (Pipline)o;
        }
        else{
            return null;
        }
    }

    private static Object loadClass(String className, JscrapyConfig arg) throws MySpiderFetalException {
        Object o = null;
        try {
            Class c = Class.forName(className);
            Constructor constructor = c.getConstructor(new Class[]{JscrapyConfig.class});
            o = constructor.newInstance(arg);
        } catch (Exception e) {
            logger.error("构造{}时出错{}", className, e);
            String errorMessage = MessageFormatter.format("构造对象{}时出错", className).getMessage();
            MySpiderFetalException exp = new MySpiderFetalException(MySpiderExceptionCode.CLASS_LOAD_ERROR);
            exp.setErrorMessage(errorMessage);
            throw  exp;
        }

        return o;
    }

}
