package scheduler;

import com.oxf1.spider.TaskConfig;
import com.oxf1.spider.config.ConfigKeys;
import com.oxf1.spider.config.ConfigOperator;
import com.oxf1.spider.config.impl.EhcacheConfigOperator;
import com.oxf1.spider.exception.MySpiderException;
import com.oxf1.spider.exception.MySpiderFetalException;
import com.oxf1.spider.request.HttpRequestMethod;
import com.oxf1.spider.request.Request;
import com.oxf1.spider.request.impl.HttpRequest;
import com.oxf1.spider.scheduler.Scheduler;
import com.oxf1.spider.scheduler.impl.LocalQueueScheduler;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import org.apache.commons.io.FileUtils;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;

/**
 * Created by cxu on 2015/6/22.
 */
public class LocalSchedulerTest {
    private TaskConfig taskConfig;
    private String queuePath = System.getProperty("user.home")+"/"+".myspider/scheduler/local/";
    private Scheduler sched;

    @BeforeClass
    public void setup() throws MySpiderFetalException {
        ConfigOperator opr = new EhcacheConfigOperator();
        taskConfig = new TaskConfig("task-id-for-test", "testTask", opr);
        taskConfig.put(ConfigKeys.LOCAL_SCHEDULE_QUEUE_PATH, queuePath);
        sched = new LocalQueueScheduler(this.taskConfig);
    }

    @AfterClass
    public void tearDown() throws IOException {
        /*清空缓存*/
        CacheManager cacheManager = CacheManager.create();
        Cache ehCache = cacheManager.getCache(ConfigKeys.MYSPIER_CONFIG_NAME);
        ehCache.removeAll();
        cacheManager.clearAll();
        cacheManager.shutdown();

        this.sched.shutdown();
        /*删除文件*/
        FileUtils.deleteQuietly(new File(queuePath));
    }

    @Test
    public void testSingleThread() throws MySpiderException {

        List<Request> req = new ArrayList<Request>();
        for(int i=0; i<1000; i++){
            Request request = new HttpRequest(Double.toHexString(Math.random()), HttpRequestMethod.HTTP_GET, null);
            req.add(request);
        }
        sched.push(req);

        int count = 0;
        while(true){
            List<Request> dequeueReq = sched.poll(1);
            count += dequeueReq.size();
            if(dequeueReq.size()==0)
                break;
        }
        assertEquals(1000, count);
    }

    @Test
    public void testMultipThread(){
        //TODO
    }
}
