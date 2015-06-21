package com.oxf1.spider.config.impl;

import com.oxf1.spider.TaskId;
import com.oxf1.spider.config.ConfigOperator;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * Created by cxu on 2015/6/21.
 */
public class EhcacheConfigOperator implements ConfigOperator {

    private static String EH_CACHE_NAME = "com.oxf1.myspider.config.task";
    private static EhcacheConfigOperator EHCACHE_CONFIG_LOADER = new EhcacheConfigOperator();

    private CacheManager cacheManager;
    private Cache ehCache;

    public static EhcacheConfigOperator instance(){
        return EHCACHE_CONFIG_LOADER;
    }

    /**
     * 构造函数
     */
    private EhcacheConfigOperator(){
        cacheManager = CacheManager.create();
        ehCache = cacheManager.getCache(EH_CACHE_NAME);
    }

    /**
     * 作为String读出
     * @param taskid
     * @param key
     * @return
     */
    public String loadString(TaskId taskid, String key){
        return (String)this.getValue(taskid, key);
    }

    /**
     * 作为Integer读出
     * @param taskid
     * @param key
     * @return 没有值得情况下返回null
     */
    public Integer loadInt(TaskId taskid, String key){
        Integer value = (Integer)this.getValue(taskid, key);
        return value;
    }

    @Override
    public void put(TaskId taskid, String key, Object value) {
        String ehCacheKey  = this.getEhCacheKey(taskid, key);
        this.ehCache.put(new Element(ehCacheKey, value));
    }

    private Object getValue(TaskId taskid, String key){
        String ehCacheKey = getEhCacheKey(taskid, key);
        Element element = this.ehCache.get(ehCacheKey);
        return (Object) element.getObjectValue();
    }

    private String getEhCacheKey(TaskId taskid, String key){
        return taskid.getId()+"_"+key;
    }
}
