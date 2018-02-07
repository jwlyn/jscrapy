package org.jscrapy.ext.dedup;

import org.jscrapy.core.config.JscrapyConfig;
import org.jscrapy.core.dedup.DeDup;
import org.jscrapy.core.request.Request;
import org.jscrapy.ext.modulecfg.RedisDedupConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * redis 实现的去重
 * Created by cxu on 2015/6/22.
 */
public class RedisDedup extends DeDup {

    private static final String DEDUP_SET_PREFIX = "jscrapy_dedup_set_";
    private JedisPool pool;

    public RedisDedup() {

    }

    public void setJscrapyConfig(JscrapyConfig jscrapyConfig) {
        super.setJscrapyConfig(jscrapyConfig);
        RedisDedupConfig redisDedepConfig = null;//(RedisDedupConfig) jscrapyConfig.get(ComponentName.DEDUP_REDIS);
        String redisHost = redisDedepConfig.getHost();
        this.pool = new JedisPool(new JedisPoolConfig(), redisHost);
    }

    /**
     * @param request
     * @return 已经存在返回true, 否则false
     */
    @Override
    protected boolean isDup(Request request) {

        try (Jedis jedis = pool.getResource()) {
            boolean isDuplicate = jedis.sismember(getDedupSetKey(), request.uniqId());
            if (!isDuplicate) {
                jedis.sadd(getDedupSetKey(), request.uniqId());
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
        String dedupSetKey = DEDUP_SET_PREFIX + getJscrapyConfig().getTaskFp();

        return dedupSetKey;
    }
}
