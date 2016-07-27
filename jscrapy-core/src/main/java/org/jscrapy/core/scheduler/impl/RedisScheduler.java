package org.jscrapy.core.scheduler.impl;

import com.alibaba.fastjson.JSONException;
import org.jscrapy.core.config.JscrapyConfig;
import org.jscrapy.core.request.impl.HttpRequest;
import org.jscrapy.core.request.Request;
import org.jscrapy.core.scheduler.Scheduler;
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
public class RedisScheduler  extends Scheduler {
    final static Logger logger = LoggerFactory.getLogger(RedisScheduler.class);
    private JedisPool pool;

    public RedisScheduler(JscrapyConfig jscrapyConfig) {
        super(jscrapyConfig);
        String redisHost = jscrapyConfig.getRedisSchedulerHost();
        this.pool = new JedisPool(new JedisPoolConfig(), redisHost);
    }

    @Override
    public int push(List<Request> requests) {

        try(Jedis jedis = this.pool.getResource()){
            for(Request req : requests){
                jedis.rpush(this.getQueueName(), req.asJson());
            }
        }

        return requests.size();
    }

    @Override
    public List<Request> poll(int n) {
        List<Request> req = new ArrayList<Request>();
        try(Jedis jedis = this.pool.getResource()){
            for(int i=0; i<n; i++){
                String reqJson = jedis.lpop(this.getQueueName());
                Request request = Request.build(reqJson, HttpRequest.class);
                req.add(request);
            }

        } catch (JSONException e) {

        }
        return req;
    }

    @Override
    public int remove(List<Request> requests) {
        //do nothing
        return 0;
    }
    /**
     * request队列的名字
     * @return
     */
    private String getQueueName(){
        return  "jscrapy_queue_"+this.getJscrapyConfig().getTaskId();
    }
}
