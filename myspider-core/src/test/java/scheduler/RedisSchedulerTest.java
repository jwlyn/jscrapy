package scheduler;

import com.oxf1.myspider.TaskConfig;
import com.oxf1.myspider.exception.MySpiderException;
import com.oxf1.myspider.request.HttpRequestMethod;
import com.oxf1.myspider.request.Request;
import com.oxf1.myspider.request.impl.HttpRequest;
import com.oxf1.myspider.scheduler.Scheduler;
import com.oxf1.myspider.scheduler.impl.RedisScheduler;
import org.testng.annotations.Test;
import util.ResourcePathUtils;

import java.io.IOException;
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
    public void test() throws MySpiderException, IOException {
        String path = ResourcePathUtils.getResourceFileAbsPath(RedisSchedulerTest.class, "/RedisSchedulerTest.yaml");
        TaskConfig taskConfig = new TaskConfig(path);
        Scheduler sched = new RedisScheduler(taskConfig);

        List<Request> request = new ArrayList<Request>();
        for(int i=0; i<100; i++){
            request.add(new HttpRequest("http://x.com", HttpRequestMethod.DELETE, null));
        }
        sched.push(request);

        request = sched.poll(100);
        assertNotNull(request);
        assertEquals(100, request.size());
    }
}
