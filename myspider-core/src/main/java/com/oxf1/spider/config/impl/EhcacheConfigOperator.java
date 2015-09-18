package com.oxf1.spider.config.impl;

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
     *
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
     * @param key
     * @return
     */
    @Override
    public Object loadValue(String key){
        return this.getValue(key);
    }

    @Override
    public void put(String key, Object value) {
        String ehCacheKey  = this.getEhCacheKey(key);
        this.ehCache.put(new Element(ehCacheKey, value));
    }

    /**
     *
     * @param key
     * @return
     */
    private Object getValue(String key){
        String ehCacheKey = getEhCacheKey(key);
        Element element = this.ehCache.get(ehCacheKey);
        if(element!=null){
            return (Object) element.getObjectValue();
        }
        return null;
    }

    /**
     *
     * @param key
     * @return
     */
    private String getEhCacheKey(String key){
        return key;
    }
}
