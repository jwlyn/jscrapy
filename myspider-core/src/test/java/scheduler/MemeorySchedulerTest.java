package scheduler;

import com.oxf1.spider.TaskConfig;
import com.oxf1.spider.exception.MySpiderException;
import com.oxf1.spider.request.HttpRequestMethod;
import com.oxf1.spider.request.Request;
import com.oxf1.spider.request.impl.HttpRequest;
import com.oxf1.spider.scheduler.Scheduler;
import com.oxf1.spider.scheduler.impl.MemoryScheduler;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * Created by cxu on 2015/9/18.
 */
public class MemeorySchedulerTest {
    /**
     * 插入N条，得到N条
     */
    @Test
    public void test() throws MySpiderException {
        TaskConfig taskConfig = new TaskConfig("task_id", "task_name");
        Scheduler sched = new MemoryScheduler(taskConfig);

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
