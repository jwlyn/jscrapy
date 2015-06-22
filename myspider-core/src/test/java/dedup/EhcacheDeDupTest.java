package dedup;

import com.oxf1.spider.TaskId;
import com.oxf1.spider.dudup.DeDup;
import com.oxf1.spider.dudup.impl.EhCacheDedup;
import com.oxf1.spider.request.HttpRequestMethod;
import com.oxf1.spider.request.Request;
import com.oxf1.spider.request.impl.HttpRequest;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

/**
 * Created by cxu on 2015/6/22.
 */
public class EhcacheDeDupTest {
    private TaskId taskid;

    @BeforeClass
    public void setup()
    {
        taskid = new TaskId("Task-Id-For-Test", "testTask");
        DeDup dp = new EhCacheDedup(taskid);
        Request rq = new HttpRequest("url", HttpRequestMethod.HTTP_DELETE, null);
        List<Request> req = new ArrayList<Request>();
        req.add(rq);
        req = dp.deDup(req);
        assertEquals(1, req.size());
    }

    @AfterClass
    public void tearDown()
    {
        CacheManager cacheManager = CacheManager.create();
        Cache ehCache = cacheManager.getCache(taskid.getTaskName());
        ehCache.removeAll();
        cacheManager.clearAll();
        cacheManager.shutdown();
    }

    @Test
    public void test(){
        //先测试写入原来一样的，返回非空
        Request rq1 = new HttpRequest("url", HttpRequestMethod.HTTP_DELETE, null);
        Request rq2 = new HttpRequest("url2", HttpRequestMethod.HTTP_DELETE, null);
        Request rq3 = new HttpRequest("url3", HttpRequestMethod.HTTP_DELETE, null);

        DeDup dp = new EhCacheDedup(taskid);
        boolean b = dp.isDup(rq1);
        assertTrue(b);

        //再测试插入一个新的
        b = dp.isDup(rq2);
        assertFalse(b);

        List<Request> req = new ArrayList<Request>();
        req.add(rq1);
        req.add(rq2);
        req.add(rq3);

        req = dp.deDup(req);
        assertEquals(1, req.size());
        assertEquals(0, dp.deDup(req).size());
    }


}
