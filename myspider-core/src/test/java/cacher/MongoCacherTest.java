package cacher;

import com.oxf1.spider.TaskConfig;
import com.oxf1.spider.cacher.Cacher;
import com.oxf1.spider.cacher.impl.MongoCacher;
import com.oxf1.spider.config.ConfigKeys;
import com.oxf1.spider.config.impl.EhcacheConfigOperator;
import com.oxf1.spider.page.Page;
import com.oxf1.spider.request.HttpRequestMethod;
import com.oxf1.spider.request.Request;
import com.oxf1.spider.request.impl.HttpRequest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;

/**
 * Created by cxu on 2015/7/12.
 */
public class MongoCacherTest {
    private Page page;
    private Request request;
    private TaskConfig taskConfig = null;
    private Cacher cacher = null;

    public MongoCacherTest(){
        request = new HttpRequest("http://oxf1.com/test", HttpRequestMethod.HTTP_DELETE, null);
        page = new Page("this is html content, hahaha!");
        page.setRequest(request);

        taskConfig = new TaskConfig("taskId", "taskName", new EhcacheConfigOperator());
        taskConfig.put(ConfigKeys.MONGODB_HOST, "localhost");
        taskConfig.put(ConfigKeys.MONGODB_PORT, 27017);
        taskConfig.put(ConfigKeys.MONGODB_CACHER_DB_NAME, "myspider_cacher");
        cacher = new MongoCacher(taskConfig);
    }

    @BeforeClass
    public void setup(){
        this.cacher.cachePage(page);
    }

    @AfterClass
    public void tearDown(){
        this.cacher.close();
    }

    @Test
    public void test(){
        Page pg = this.cacher.loadPage(request);
        assertNotNull(pg);
    }
}
