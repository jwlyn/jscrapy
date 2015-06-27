package dedup;

import com.oxf1.spider.TaskId;
import com.oxf1.spider.config.ConfigKeys;
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
import static org.testng.Assert.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

/**
 * Created by cxu on 2015/6/27.
 */
public class RedisDedupTest {
    private TaskId taskid;
    Request rq = new HttpRequest("http://baidu.com", HttpRequestMethod.HTTP_DELETE, null);

    @BeforeClass
    public void setup()
    {
        taskid = new TaskId("Task-Id-For-Test", "testTask");
        EhcacheConfigOperator.instance().put(taskid, ConfigKeys.REDIS_DEDUP_SERVER, "120.26.0.133");
        DeDup dp = new RedisDedup(taskid);

        List<Request> req = new ArrayList<Request>();
        req.add(rq);
        req = dp.deDup(req);
        assertEquals(1, req.size());
    }

    @AfterClass
    public void tearDown()
    {
        JedisPool pool = new JedisPool(new JedisPoolConfig(), "120.26.0.133");
        Jedis jedis = pool.getResource();
        jedis.del(taskid.getId());
        jedis.close();
        pool.close();
    }

    @Test
    public void test(){

        Request rq2 = new HttpRequest("url2", HttpRequestMethod.HTTP_DELETE, null);
        Request rq3 = new HttpRequest("url3", HttpRequestMethod.HTTP_DELETE, null);

        DeDup dp = new RedisDedup(taskid);
        boolean b = dp.isDup(rq);
        assertTrue(b);

        //再测试插入一个新的
        b = dp.isDup(rq2);
        assertFalse(b);

        List<Request> req = new ArrayList<Request>();
        req.add(rq);
        req.add(rq2);
        req.add(rq3);

        req = dp.deDup(req);
        assertEquals(1, req.size());
        assertEquals(0, dp.deDup(req).size());
    }
}
