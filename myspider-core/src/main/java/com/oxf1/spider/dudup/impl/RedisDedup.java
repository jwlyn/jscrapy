package com.oxf1.spider.dudup.impl;

import com.oxf1.spider.TaskConfig;
import com.oxf1.spider.config.ConfigKeys;
import com.oxf1.spider.dudup.DeDup;
import com.oxf1.spider.request.Request;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * redis 实现的去重
 * Created by cxu on 2015/6/22.
 */
public class RedisDedup extends DeDup {

    private static final String DEDUP_SET_PREFIX="myspider_dedup_set_";
    private JedisPool pool;

    public RedisDedup(TaskConfig taskConfig) {
        super(taskConfig);
        String redisHost = taskConfig.loadString(ConfigKeys.REDIS_DEDUP_SERVER);
        this.pool = new JedisPool(new JedisPoolConfig(), redisHost);
    }

    /**
     *
     * @param request
     * @return 已经存在返回true, 否则false
     */
    @Override
    protected boolean isDup(Request request) {

        try (Jedis jedis = pool.getResource()){
            boolean isDuplicate = jedis.sismember(getDedupSetKey(), request.fp());
            if (!isDuplicate) {
                jedis.sadd(getDedupSetKey(), request.fp());
            }
            return isDuplicate;
        }
    }

    @Override
    public void close() {
        try (Jedis jedis = pool.getResource()){
            jedis.del(this.getDedupSetKey());
        }
    }

    /**
     * 去重的redis 集合(Set)的key
     * @return
     */
    private String getDedupSetKey(){
        return DEDUP_SET_PREFIX + getTaskConfig().getTaskId();
    }

}
