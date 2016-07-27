package org.jscrapy.core.cacher.impl;

import org.jscrapy.core.config.JscrapyConfig;
import org.jscrapy.core.cacher.Cacher;
import org.jscrapy.core.exception.MySpiderExceptionCode;
import org.jscrapy.core.exception.MySpiderFetalException;
import org.jscrapy.core.exception.MySpiderRecoverableException;
import org.jscrapy.core.page.Page;
import org.jscrapy.core.request.Request;
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
     * @param JscrapyConfig
     */
    public LocalDiskCacher(JscrapyConfig JscrapyConfig) throws MySpiderFetalException {
        super(JscrapyConfig);
        String taskWorkDir = getTaskWorkDir();//组合出这个任务在本地磁盘的目录

        try {
            FileUtils.forceMkdir(new File(taskWorkDir));
        } catch (IOException e) {

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

            } catch (IOException e) {

                MySpiderRecoverableException exp = new MySpiderRecoverableException(MySpiderExceptionCode.DISK_CACHER_READ_ERROR);
                exp.setErrorMessage(e.getLocalizedMessage());
                throw exp;
            }
        }
        else{
            //log(logger, "info", "缓存没有命中文件{}", file);
        }

        return page;
    }

    @Override
    public void cachePage(Page page) throws MySpiderFetalException {
        String file = this.getCacheFilePath(page.getRequest());
        try {
            //覆盖方式写
            FileUtils.write(new File(file), page.getRawText(), StandardCharsets.UTF_8, false);
            //log(logger, "info", "缓存文件{}", file);
        } catch (IOException e) {
            //log(logger, "error", "缓存文件{}时发生异常{}", file, e);
            MySpiderFetalException exp = new MySpiderFetalException(MySpiderExceptionCode.DISK_CACHER_CACHE_FILE_ERROR);
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
        JscrapyConfig JscrapyConfig = getJscrapyConfig();
        return JscrapyConfig.getTaskWorkDir();
    }

    /**
     *
     * @return
     */
    private String getTaskCacheDir() {
        JscrapyConfig JscrapyConfig = getJscrapyConfig();
        return JscrapyConfig.getTaskCacheDir();
    }


}
