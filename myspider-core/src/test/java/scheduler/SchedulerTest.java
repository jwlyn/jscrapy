package scheduler;

import com.oxf1.myspider.TaskConfig;
import com.oxf1.myspider.dedup.DeDup;
import com.oxf1.myspider.exception.MySpiderException;
import com.oxf1.myspider.exception.MySpiderFetalException;
import com.oxf1.myspider.request.HttpRequestMethod;
import com.oxf1.myspider.request.Request;
import com.oxf1.myspider.request.impl.HttpRequest;
import com.oxf1.myspider.scheduler.Scheduler;
import com.oxf1.myspider.scheduler.impl.MemoryScheduler;
import com.oxf1.myspider.scheduler.impl.RedisScheduler;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.ResourcePathUtils;

import java.util.ArrayList;
import java.util.List;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;

/**
 * Created by cxu on 2015/12/9.
 */
public class SchedulerTest {

    @DataProvider(name = "dp")
    public Object[][] dataProvider() throws MySpiderFetalException {
        return new Scheduler[][]{
                //{initEhcacheDedup()} ,
                {initMemoryScheduler()},
                {initDiskScheduler()},
                {initRedisScheduler()},
        };
    }

    @Test(dataProvider = "dp")
    public void test(Scheduler scheduler) throws MySpiderException {

        List<Request> request = new ArrayList<Request>();
        for(int i=0; i<100; i++){
            request.add(new HttpRequest("http://x.com", HttpRequestMethod.DELETE, null));
        }
        scheduler.push(request);

        request = scheduler.poll(100);
        assertNotNull(request);
        assertEquals(100, request.size());
    }

    private Scheduler initMemoryScheduler() throws MySpiderFetalException {
        String path = ResourcePathUtils.getResourceFileAbsPath(SchedulerTest.class, "/MemorySchedulerTest.yaml");
        TaskConfig taskConfig = new TaskConfig(path);
        Scheduler sched = new MemoryScheduler(taskConfig);
        return sched;
    }

    private Scheduler initRedisScheduler() throws MySpiderFetalException {
        String path = ResourcePathUtils.getResourceFileAbsPath(SchedulerTest.class, "/RedisSchedulerTest.yaml");
        TaskConfig taskConfig = new TaskConfig(path);
        Scheduler sched = new RedisScheduler(taskConfig);
        return sched;
    }

    private Scheduler initDiskScheduler() throws MySpiderFetalException {
        String path = ResourcePathUtils.getResourceFileAbsPath(SchedulerTest.class, "/DiskSchedulerTest.yaml");
        TaskConfig taskConfig = new TaskConfig(path);
        Scheduler sched = new MemoryScheduler(taskConfig);
        return sched;
    }
}
