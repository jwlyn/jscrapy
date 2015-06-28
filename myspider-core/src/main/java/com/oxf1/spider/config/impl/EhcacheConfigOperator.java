package com.oxf1.spider.config.impl;

import com.oxf1.spider.TaskConfig;
import com.oxf1.spider.config.ConfigKeys;
import com.oxf1.spider.config.ConfigOperator;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * Created by cxu on 2015/6/21.
 */
public class EhcacheConfigOperator implements ConfigOperator {
    private CacheManager cacheManager;
    private Cache ehCache;

    /**
     * 构造函数
     */
    public EhcacheConfigOperator(){
        if(cacheManager==null){
            synchronized (EhcacheConfigOperator.class){
                if(cacheManager==null){
                    cacheManager = CacheManager.create();
                    ehCache = cacheManager.getCache(ConfigKeys.MYSPIER_CONFIG_NAME);
                }
            }
        }
    }

    /**
     * 作为String读出
     * @param taskid
     * @param key
     * @return
     */
    public String loadString(TaskConfig taskid, String key){
        return (String)this.getValue(taskid, key);
    }

    /**
     * 作为Integer读出
     * @param taskid
     * @param key
     * @return 没有值得情况下返回null
     */
    public Integer loadInt(TaskConfig taskid, String key){
        Integer value = (Integer)this.getValue(taskid, key);
        return value;
    }

    @Override
    public void put(TaskConfig taskid, String key, Object value) {
        String ehCacheKey  = this.getEhCacheKey(taskid, key);
        this.ehCache.put(new Element(ehCacheKey, value));
    }

    private Object getValue(TaskConfig taskid, String key){
        String ehCacheKey = getEhCacheKey(taskid, key);
        Element element = this.ehCache.get(ehCacheKey);
        return (Object) element.getObjectValue();
    }

    private String getEhCacheKey(TaskConfig taskid, String key){
        return taskid.getTaskId()+"_"+key;
    }
}
