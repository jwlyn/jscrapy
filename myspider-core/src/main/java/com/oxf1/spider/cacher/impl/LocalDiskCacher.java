package com.oxf1.spider.cacher.impl;

import com.oxf1.spider.TaskConfig;
import com.oxf1.spider.cacher.Cacher;
import com.oxf1.spider.exception.MySpiderExceptionCode;
import com.oxf1.spider.exception.MySpiderFetalException;
import com.oxf1.spider.exception.MySpiderRecoverableException;
import com.oxf1.spider.page.Page;
import com.oxf1.spider.request.Request;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Created by cxu on 2015/7/12.
 */
public class LocalDiskCacher extends Cacher {
    final static Logger logger = LoggerFactory.getLogger(LocalDiskCacher.class);

    /**
     * 构造函数
     * @param taskConfig
     */
    public LocalDiskCacher(TaskConfig taskConfig) throws MySpiderFetalException {
        super(taskConfig);
        String taskWorkDir = getTaskWorkDir();//组合出这个任务在本地磁盘的目录

        try {
            FileUtils.forceMkdir(new File(taskWorkDir));
        } catch (IOException e) {
            logger.error("创建目录{}时失败{}", taskWorkDir, e);
            MySpiderFetalException exp = new MySpiderFetalException(MySpiderExceptionCode.DISK_CACHER_MK_DIR_ERROR);
            exp.setErrorMessage(e.getLocalizedMessage());
            throw exp;
        }
    }

    @Override
    public Page loadPage(Request request) throws MySpiderRecoverableException {
        String file = this.getCacheFilePath(request);
        Page page = null;
        File f = new File(file);
        if (f.exists()) {
            try {
                String pageContent = FileUtils.readFileToString(new File(file));
                page = new Page(pageContent);
                page.setRequest(request);
                page.setIsFromCache(true);
                logger.info("缓存命中文件{}", file);
            } catch (IOException e) {
                logger.error("读文件{}时失败{}", file, e);
                MySpiderRecoverableException exp = new MySpiderRecoverableException(MySpiderExceptionCode.DISK_CACHER_READ_ERROR);
                exp.setErrorMessage(e.getLocalizedMessage());
                throw exp;
            }
        }
        else{
            logger.info("缓存没有命中文件{}", file);
        }

        return page;
    }

    @Override
    public void cachePage(Page page) throws MySpiderFetalException {
        String file = this.getCacheFilePath(page.getRequest());
        try {
            //覆盖方式写
            FileUtils.write(new File(file), page.getRawText(), StandardCharsets.UTF_8, false);
            logger.info("缓存文件{}", file);
        } catch (IOException e) {
            logger.error("缓存文件{}时发生异常{}", file, e);
            MySpiderFetalException exp = new MySpiderFetalException(MySpiderExceptionCode.DISK_CACHER_CACHE_FILE_ERROR);
            exp.setErrorMessage(e.getLocalizedMessage());
            throw exp;
        }
    }


    @Override
    public void close() throws MySpiderRecoverableException {
        String taskWorkDir = getTaskWorkDir();
        try {
            FileUtils.deleteDirectory(new File(taskWorkDir));
        } catch (IOException e) {
            logger.error("清空目录{}时发生异常{}", taskWorkDir, e);
            MySpiderRecoverableException exp = new MySpiderRecoverableException(MySpiderExceptionCode.DISK_CACHER_DEL_DIR_ERROR);
            exp.setErrorMessage(e.getLocalizedMessage());
            throw exp;
        }
    }

    /**
     * 获取缓存文件的绝对路径
     * @param request
     * @return
     */
    private String getCacheFilePath(Request request){
        String file = "_myspider_not_exists.html";
        if(request!=null){
            file = request.fp() + ".html";
        }
        return getTaskCacheDir() + file;
    }

    /**
     *
     * @return
     */
    private String getTaskWorkDir() {
        TaskConfig taskConfig = getTaskConfig();
        return taskConfig.getTaskWorkDir();
    }

    /**
     *
     * @return
     */
    private String getTaskCacheDir() {
        TaskConfig taskConfig = getTaskConfig();
        return taskConfig.getTaskCacheDir();
    }


}
