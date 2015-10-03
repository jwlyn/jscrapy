package scheduler;

import com.oxf1.spider.TaskConfig;
import com.oxf1.spider.exception.MySpiderException;
import com.oxf1.spider.exception.MySpiderFetalException;
import com.oxf1.spider.request.HttpRequestMethod;
import com.oxf1.spider.request.Request;
import com.oxf1.spider.request.impl.HttpRequest;
import com.oxf1.spider.scheduler.Scheduler;
import com.oxf1.spider.scheduler.impl.FileQueueScheduler;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import util.ResourcePathUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;

/**
 * Created by cxu on 2015/6/22.
 */
public class LocalSchedulerTest {
    private TaskConfig taskConfig;
    private Scheduler sched;

    @BeforeClass
    public void setup() throws MySpiderFetalException, IOException {
        String path = ResourcePathUtils.getResourceFileAbsPath(LocalSchedulerTest.class, "/LocalSchedulerTest.yaml");
        taskConfig = new TaskConfig(path);
        sched = new FileQueueScheduler(this.taskConfig);
    }

    @AfterClass
    public void tearDown() throws IOException {

        this.sched.close();
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
