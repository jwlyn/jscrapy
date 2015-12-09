package scheduler;

import com.oxf1.myspider.TaskConfig;
import com.oxf1.myspider.exception.MySpiderException;
import com.oxf1.myspider.exception.MySpiderFetalException;
import com.oxf1.myspider.request.HttpRequestMethod;
import com.oxf1.myspider.request.Request;
import com.oxf1.myspider.request.impl.HttpRequest;
import com.oxf1.myspider.scheduler.Scheduler;
import com.oxf1.myspider.scheduler.impl.MemoryScheduler;
import org.testng.annotations.Test;
import util.ResourcePathUtils;

import java.util.ArrayList;
import java.util.List;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;

/**
 * Created by cxu on 2015/12/9.
 */
public class DiskSchedulerTest {
    @Test
    public void test() throws MySpiderException {
        String path = ResourcePathUtils.getResourceFileAbsPath(DiskSchedulerTest.class, "/DiskSchedulerTest.yaml");
        TaskConfig taskConfig = new TaskConfig(path);
        Scheduler sched = new MemoryScheduler(taskConfig);

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
