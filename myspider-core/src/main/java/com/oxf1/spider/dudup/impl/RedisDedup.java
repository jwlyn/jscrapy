package com.oxf1.spider.dudup.impl;

import com.oxf1.spider.TaskId;
import com.oxf1.spider.config.ConfigKeys;
import com.oxf1.spider.config.impl.EhcacheConfigOperator;
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

    private JedisPool pool;

    public RedisDedup(TaskId taskid) {
        super(taskid);
        String redisHost = EhcacheConfigOperator.instance().loadString(taskid, ConfigKeys.REDIS_DEDUP_SERVER);
        this.pool = new JedisPool(new JedisPoolConfig(), redisHost);
    }

    /**
     *
     * @param request
     * @return 已经存在返回true, 否则false
     */
    @Override
    public boolean isDup(Request request) {

        try (Jedis jedis = pool.getResource()){
            boolean isDuplicate = jedis.sismember(getDedupSetKey(), request.fp());
            if (!isDuplicate) {
                jedis.sadd(getDedupSetKey(), request.fp());
            }
            return isDuplicate;
        }
    }

    /**
     * 去重的redis 集合(Set)的key
     * @return
     */
    private String getDedupSetKey(){
        return "myspider_dedup_" + getTaskid().getId();
    }
}
