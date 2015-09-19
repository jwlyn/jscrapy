package com.oxf1.spider.dedup.impl;

import com.oxf1.spider.TaskConfig;
import com.oxf1.spider.dedup.DeDup;
import com.oxf1.spider.request.Request;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * Created by cxu on 2015/6/22.
 */
public class EhCacheDedup extends DeDup {
    CacheManager cacheManager;
    Cache ehCache;


    public EhCacheDedup(TaskConfig taskid) {
        super(taskid);
        cacheManager = CacheManager.create();
        cacheManager.addCacheIfAbsent(getTaskConfig().getTaskFp());
        ehCache = cacheManager.getCache(getTaskConfig().getTaskFp());
    }

    @Override
    protected boolean isDup(Request request) {
        String fp = request.fp();
        Element el = new Element(fp, 1);
        el = ehCache.putIfAbsent(el);
        if(el==null){//非重复
            return false;
        }
        return true;
    }

    @Override
    public void close() {
        ehCache.removeAll();
    }
}
