package org.jscrapy.contrib.dedup;

import org.jscrapy.core.config.JscrapyConfig;
import org.jscrapy.core.dedup.DeDup;
import org.jscrapy.core.request.Request;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * redis 实现的去重
 * Created by cxu on 2015/6/22.
 */
public class RedisDedup extends DeDup {

    private static final String DEDUP_SET_PREFIX = "myspider_dedup_set_";
    private JedisPool pool;

    public RedisDedup(JscrapyConfig jscrapyConfig) {
        super(jscrapyConfig);
        String redisHost = jscrapyConfig.getRedisSchedulerHost();
        this.pool = new JedisPool(new JedisPoolConfig(), redisHost);
    }

    /**
     * @param request
     * @return 已经存在返回true, 否则false
     */
    @Override
    protected boolean isDup(Request request) {

        try (Jedis jedis = pool.getResource()) {
            boolean isDuplicate = jedis.sismember(getDedupSetKey(), request.fp());
            if (!isDuplicate) {
                jedis.sadd(getDedupSetKey(), request.fp());
            }
            return isDuplicate;
        }
    }

    /**
     * 去重的redis 集合(Set)的key
     *
     * @return
     */
    private String getDedupSetKey() {
        String dedupSetKey = DEDUP_SET_PREFIX + getJscrapyConfig().getTaskId();

        return dedupSetKey;
    }

}
