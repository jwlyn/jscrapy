package org.jscrapy.core.cacher.impl;

import org.jscrapy.core.cacher.Cacher;
import org.jscrapy.core.config.JscrapyConfig;
import org.jscrapy.core.dal.cache.PageCacheDo;
import org.jscrapy.core.dal.cache.PageCacheMapper;
import org.jscrapy.core.page.Page;
import org.jscrapy.core.request.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by cxu on 2015/7/12.
 */

public class H2Cacher extends Cacher {
    final static Logger logger = LoggerFactory.getLogger(H2Cacher.class);

    @Autowired
    private PageCacheMapper pageCacheMapper;

    /**
     * 构造函数
     *
     * @param jscrapyConfig
     */
    public H2Cacher(JscrapyConfig jscrapyConfig)  {
        setJscrapyConfig(jscrapyConfig);
        initCache();
    }

    public H2Cacher() {

    }

    public void setJscrapyConfig(JscrapyConfig jscrapyConfig) {
        super.setJscrapyConfig(jscrapyConfig);
        initCache();
    }

    @Override
    public Page loadPage(HttpRequest request) {
        String taskName = getJscrapyConfig().getTaskName();
        String pageId = request.uniqId();
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
        pageCacheDo.setPageId(page.getRequest().uniqId());
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
