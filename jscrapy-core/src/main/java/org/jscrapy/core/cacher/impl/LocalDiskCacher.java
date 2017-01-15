package org.jscrapy.core.cacher.impl;

import org.jscrapy.core.cacher.Cacher;
import org.jscrapy.core.config.JscrapyConfig;
import org.jscrapy.core.dal.cache.PageCacheDo;
import org.jscrapy.core.dal.cache.PageCacheMapper;
import org.jscrapy.core.page.Page;
import org.jscrapy.core.request.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by cxu on 2015/7/12.
 */
@Component
public class LocalDiskCacher extends Cacher {
    final static Logger logger = LoggerFactory.getLogger(LocalDiskCacher.class);
    @Autowired
    private PageCacheMapper pageCacheMapper;

    /**
     * 构造函数
     *
     * @param jscrapyConfig
     */
    public LocalDiskCacher(JscrapyConfig jscrapyConfig)  {
        super(jscrapyConfig);
        initCache();
    }

    public LocalDiskCacher() {

    }

    public void setJscrapyConfig(JscrapyConfig jscrapyConfig) {
        super.setJscrapyConfig(jscrapyConfig);
        initCache();
    }

    @Override
    public Page loadPage(Request request) {
        String taskName = getJscrapyConfig().getTaskName();
        String pageId = request.fp();
        PageCacheDo pageCacheDo = pageCacheMapper.find(taskName, pageId);
        Page page = new Page();
        if (pageCacheDo != null) {
            page.setIsFromCache(true);
            page.setRawText(pageCacheDo.getPageContent());
            page.setRequest(request);
        }

        return page;
    }

    @Override
    public void cachePage(Page page) {
        String tableName = getJscrapyConfig().getTaskName();
        PageCacheDo pageCacheDo = new PageCacheDo();
        pageCacheDo.setPageId(page.getRequest().fp());
        pageCacheDo.setPageContent(page.getRawText());

        pageCacheMapper.insert(tableName, pageCacheDo);
    }

    /**
     * 为任务创建缓存表
     */
    private void initCache() {
        String taskName = getJscrapyConfig().getTaskName();
        pageCacheMapper.createCacherTable(taskName);
    }
}
