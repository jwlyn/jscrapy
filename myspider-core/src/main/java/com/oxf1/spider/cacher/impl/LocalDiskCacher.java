package com.oxf1.spider.cacher.impl;

import com.oxf1.spider.TaskConfig;
import com.oxf1.spider.cacher.Cacher;
import com.oxf1.spider.config.ConfigKeys;
import com.oxf1.spider.config.SysDefaultConfig;
import com.oxf1.spider.page.Page;
import com.oxf1.spider.request.Request;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
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
    //缓存文件的最外层目录
    private String cacheDir;

    /**
     * 构造函数
     * @param taskConfig
     */
    public LocalDiskCacher(TaskConfig taskConfig) {
        super(taskConfig);
        String spiderWorkDir = taskConfig.getTaskWorkDir();
        if(StringUtils.isBlank(spiderWorkDir)){
            spiderWorkDir = SysDefaultConfig.DEFAULT_SPIDER_WORK_DIR;
        }
        spiderWorkDir = spiderWorkDir + taskConfig.getTaskFp() + File.separator + "cacher" + File.separator;
        this.cacheDir = spiderWorkDir;//组合出这个任务在本地磁盘的目录
        taskConfig.put(ConfigKeys.RT_LOCAL_CACHER_DIR, this.cacheDir);

        try {
            FileUtils.forceMkdir(new File(this.cacheDir));
        } catch (IOException e) {
            //TODO
            e.printStackTrace();
            logger.error("创建目录{}时失败{}", cacheDir, e);
        }
    }

    @Override
    public Page loadPage(Request request) {
        String file = this.getCacheFilePath(request);
        Page page = null;
        File f = new File(file);
        if (f.exists()) {
            try {
                String pageContent = FileUtils.readFileToString(new File(file));
                page = new Page(pageContent);
                page.setRequest(request);
                page.setIsFromCache(true);
                //TODO log
                logger.info("缓存命中文件{}", file);
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("读文件{}时失败{}", file, e);
            }
        }
        else{
            logger.info("缓存没有命中文件{}", file);
        }

        return page;
    }

    @Override
    public void cachePage(Page page) {
        String file = this.getCacheFilePath(page.getRequest());
        try {
            //覆盖方式写
            FileUtils.write(new File(file), page.getRawText(), StandardCharsets.UTF_8, false);
            logger.info("缓存文件{}", file);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("缓存文件{}时发生异常{}", file, e);
        }
    }


    @Override
    public void close() {
        try {
            FileUtils.deleteDirectory(new File(this.cacheDir));
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("清空目录{}时发生异常{}", cacheDir, e);
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
        return this.cacheDir + file;
    }
}
