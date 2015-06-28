package dedup;

import com.oxf1.spider.TaskConfig;
import com.oxf1.spider.config.ConfigKeys;
import com.oxf1.spider.config.ConfigOperator;
import com.oxf1.spider.config.impl.EhcacheConfigOperator;
import com.oxf1.spider.dudup.DeDup;
import com.oxf1.spider.dudup.impl.RedisDedup;
import com.oxf1.spider.request.HttpRequestMethod;
import com.oxf1.spider.request.Request;
import com.oxf1.spider.request.impl.HttpRequest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;

/**
 * Created by cxu on 2015/6/27.
 */
public class RedisDedupTest {
    private TaskConfig taskConfig;
    Request rq = new HttpRequest("http://baidu.com", HttpRequestMethod.HTTP_DELETE, null);

    @BeforeClass
    public void setup()
    {
        ConfigOperator opr = new EhcacheConfigOperator();
        taskConfig = new TaskConfig("Task-Id-For-Test", "testTask", opr);
        taskConfig.put(taskConfig, ConfigKeys.REDIS_DEDUP_SERVER, "localhost");;
        DeDup dp = new RedisDedup(taskConfig);

        List<Request> req = new ArrayList<Request>();
        req.add(rq);
        req = dp.deDup(req);
        assertEquals(1, req.size());
    }

    @AfterClass
    public void tearDown()
    {
        JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost");
        Jedis jedis = pool.getResource();
        jedis.del(taskConfig.getTaskId());
        jedis.close();
        pool.close();
    }

    @Test
    public void test(){

        Request rq1 = rq;
        Request rq2 = new HttpRequest("url2", HttpRequestMethod.HTTP_DELETE, null);
        Request rq3 = new HttpRequest("url3", HttpRequestMethod.HTTP_DELETE, null);

        DeDup dp = new RedisDedup(taskConfig);

        List<Request> req = new ArrayList<Request>();
        req.add(rq1);
        req.add(rq2);
        req.add(rq3);

        req = dp.deDup(req);
        assertEquals(2, req.size());
        assertEquals(0, dp.deDup(req).size());
    }
}
