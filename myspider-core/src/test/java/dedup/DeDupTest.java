package dedup;

import com.oxf1.spider.TaskConfig;
import com.oxf1.spider.dedup.DeDup;
import com.oxf1.spider.dedup.impl.EhCacheDedup;
import com.oxf1.spider.dedup.impl.MemoryDedup;
import com.oxf1.spider.dedup.impl.MongoDedup;
import com.oxf1.spider.dedup.impl.RedisDedup;
import com.oxf1.spider.request.HttpRequestMethod;
import com.oxf1.spider.request.Request;
import com.oxf1.spider.request.impl.HttpRequest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.ResourcePathUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;

/**
 * Created by cxu on 2015/9/19.
 */
public class DeDupTest {
    private Request rq = new HttpRequest("http://url1", HttpRequestMethod.HTTP_DELETE, null);

    @DataProvider(name="dp")
    public DeDup[][] dataProvider() throws IOException {
        return new DeDup[][]{
                {initEhcacheDedup()} ,
                {initMemoryDedup()},
                {initMongoDedup()},
                {initRedisDedup()},
        };
    }


    @Test(dataProvider = "dp")
    public void test(DeDup dedup){

        List<Request> req1 = new ArrayList<Request>();
        req1.add(rq);
        req1 = dedup.deDup(req1);
        assertEquals(1, req1.size());

        //先测试写入原来一样的，返回非空
        Request rq1 = rq;
        Request rq2 = new HttpRequest("http://url2", HttpRequestMethod.HTTP_DELETE, null);
        Request rq3 = new HttpRequest("http://url3", HttpRequestMethod.HTTP_DELETE, null);

        List<Request> req = new ArrayList<Request>();
        req.add(rq1);
        req.add(rq2);
        req.add(rq3);

        req = dedup.deDup(req);
        assertEquals(2, req.size());
        assertEquals(0, dedup.deDup(req).size());

        dedup.close();//清空现场，否则下一次运行会出错
    }

    /**
     *
     * @return
     */
    private DeDup initEhcacheDedup() throws IOException {
        String path = ResourcePathUtils.getResourceFileAbsPath(DeDupTest.class, "/EhCacheDedupTest.yaml");
        TaskConfig taskConfig = new TaskConfig(path);
        DeDup dp = new EhCacheDedup(taskConfig);
        return dp;
    }

    /**
     *
     * @return
     */
    private DeDup initMemoryDedup() throws IOException {
        String path = ResourcePathUtils.getResourceFileAbsPath(DeDupTest.class, "/MemoryDedupTest.yaml");
        TaskConfig taskConfig = new TaskConfig(path);
        DeDup dp = new MemoryDedup(taskConfig);
        return dp;
    }

    /**
     *
     * @return
     */
    private DeDup initMongoDedup() throws IOException {
        String path = ResourcePathUtils.getResourceFileAbsPath(DeDupTest.class, "/MongoDedupTest.yaml");
        TaskConfig taskConfig = new TaskConfig(path);
        DeDup dp = new MongoDedup(taskConfig);
        return dp;
    }

    /**
     *
     * @return
     */
    private DeDup initRedisDedup() throws IOException {
        String path = ResourcePathUtils.getResourceFileAbsPath(DeDupTest.class, "/RedisDedupTest.yaml");
        TaskConfig taskConfig = new TaskConfig(path);
        DeDup dp = new RedisDedup(taskConfig);
        return dp;
    }
}