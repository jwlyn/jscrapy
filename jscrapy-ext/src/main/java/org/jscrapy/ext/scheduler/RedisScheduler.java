package org.jscrapy.ext.scheduler;

import com.alibaba.fastjson.JSONException;
import org.jscrapy.ext.modulecfg.RedisSchedulerConfig;
import org.jscrapy.core.ConfigDriver;
import org.jscrapy.core.config.ComponentName;
import org.jscrapy.core.config.JscrapyConfig;
import org.jscrapy.core.request.Request;
import org.jscrapy.core.request.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cxu on 2015/6/21.
 */
public class RedisScheduler extends ConfigDriver {
    final static Logger logger = LoggerFactory.getLogger(RedisScheduler.class);
    private JedisPool pool;

    public RedisScheduler(JscrapyConfig jscrapyConfig) {
        setJscrapyConfig(jscrapyConfig);
        RedisSchedulerConfig redisDedupConfig = (RedisSchedulerConfig) jscrapyConfig.get(ComponentName.DEDUP_REDIS);
        String redisHost = redisDedupConfig.getHost();
        this.pool = new JedisPool(new JedisPoolConfig(), redisHost);
    }


    public int push(List<Request> requests) {

        try (Jedis jedis = this.pool.getResource()) {
            for (Request req : requests) {
                jedis.rpush(this.getQueueName(), req.asJson());
            }
        }

        return requests.size();
    }


    public List<Request> poll(int n) {
        List<Request> req = new ArrayList<Request>();
        try (Jedis jedis = this.pool.getResource()) {
            for (int i = 0; i < n; i++) {
                String reqJson = jedis.lpop(this.getQueueName());
                HttpRequest request = HttpRequest.build(reqJson);
                req.add(request);
            }

        } catch (JSONException e) {

        }
        return req;
    }



    /**
     * request队列的名字
     *
     * @return
     */
    private String getQueueName() {
        return "jscrapy_queue_" + this.getJscrapyConfig().getTaskId();
    }
}
