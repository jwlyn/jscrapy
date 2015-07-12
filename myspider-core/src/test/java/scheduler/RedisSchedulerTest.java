package scheduler;

import com.oxf1.spider.TaskConfig;
import com.oxf1.spider.config.ConfigKeys;
import com.oxf1.spider.config.ConfigOperator;
import com.oxf1.spider.config.impl.EhcacheConfigOperator;
import com.oxf1.spider.exception.MySpiderException;
import com.oxf1.spider.request.HttpRequestMethod;
import com.oxf1.spider.request.Request;
import com.oxf1.spider.request.impl.HttpRequest;
import com.oxf1.spider.scheduler.Scheduler;
import com.oxf1.spider.scheduler.impl.RedisScheduler;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * Created by cxu on 2015/6/27.
 */
public class RedisSchedulerTest {

    /**
     * 插入N条，得到N条
     */
    @Test
    public void test() throws MySpiderException {
        ConfigOperator opr = new EhcacheConfigOperator();
        TaskConfig taskConfig = new TaskConfig("tid", "test-task", opr);
        taskConfig.put(ConfigKeys.REDIS_DEDUP_SERVER, "localhost");

        Scheduler sched = new RedisScheduler(taskConfig);

        List<Request> request = new ArrayList<Request>();
        for(int i=0; i<100; i++){
            request.add(new HttpRequest("http://x.com", HttpRequestMethod.HTTP_DELETE, null));
        }
        sched.push(request);

        request = sched.poll(100);
        assertNotNull(request);
        assertEquals(100, request.size());
    }
}
