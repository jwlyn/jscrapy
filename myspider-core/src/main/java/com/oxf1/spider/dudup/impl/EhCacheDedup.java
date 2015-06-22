package com.oxf1.spider.dudup.impl;

import com.oxf1.spider.TaskId;
import com.oxf1.spider.dudup.DeDup;
import com.oxf1.spider.request.Request;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cxu on 2015/6/22.
 */
public class EhCacheDedup extends DeDup {
    CacheManager cacheManager;
    Cache ehCache;


    public EhCacheDedup(TaskId taskid) {
        super(taskid);
        cacheManager = CacheManager.create();
        cacheManager.addCacheIfAbsent(getTaskid().getTaskName());
        ehCache = cacheManager.getCache(getTaskid().getTaskName());
    }

    @Override
    public boolean isDup(Request request) {
        String fp = request.fp();
        Element el = new Element(fp, 1);
        el = ehCache.putIfAbsent(el);
        if(el==null){//非重复
            return false;
        }
        return true;
    }

    @Override
    public List<Request> deDup(List<Request> request) {
        List<Request> req = new ArrayList<Request>(request.size());
        for(Request url : request){
            if(!this.isDup(url)){
                req.add(url);
            }
        }
        return req;
    }
}
