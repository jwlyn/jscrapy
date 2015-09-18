package cacher;

import com.oxf1.spider.TaskConfig;
import com.oxf1.spider.cacher.Cacher;
import com.oxf1.spider.cacher.impl.LocalDiskCacher;
import com.oxf1.spider.config.ConfigOperator;
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
public class LocalDiskCacherTest {

    private Page page;
    private Request request;
    private Cacher cacher;

    public LocalDiskCacherTest(){
        request = new HttpRequest("http://oxf1.com/test", HttpRequestMethod.HTTP_DELETE, null);
        page = new Page("this is html content, hahaha!");
        page.setRequest(request);
    }

    @BeforeClass
    public void setup(){
        ConfigOperator opr = new EhcacheConfigOperator();
        TaskConfig taskConfig = new TaskConfig("task-id-for-test", "testTask", opr);
        this.cacher = new LocalDiskCacher(taskConfig);
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
