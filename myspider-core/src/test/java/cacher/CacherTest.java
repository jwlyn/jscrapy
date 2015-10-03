package cacher;

import com.oxf1.spider.TaskConfig;
import com.oxf1.spider.cacher.Cacher;
import com.oxf1.spider.cacher.impl.LocalDiskCacher;
import com.oxf1.spider.cacher.impl.MongoCacher;
import com.oxf1.spider.page.Page;
import com.oxf1.spider.request.HttpRequestMethod;
import com.oxf1.spider.request.Request;
import com.oxf1.spider.request.impl.HttpRequest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.ResourcePathUtils;

import java.io.IOException;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.fail;

/**
 * Created by cxu on 2015/9/19.
 */
public class CacherTest {
    private Page page;
    private Request request;

    @BeforeClass
    public void setup(){
        request = new HttpRequest("http://oxf1.com/test", HttpRequestMethod.HTTP_DELETE, null);
        page = new Page("this is html content, hahaha!");
        page.setRequest(request);
    }

    @DataProvider(name="dp")
    public Cacher[][] dataProvider(){
        return new Cacher[][]{
                {initLocalDiskCacher()} ,
                {initMongoCacher()}
        };
    }

    @Test(dataProvider = "dp")
    public void test(Cacher cacher){
        cacher.cachePage(page);
        Page pg = cacher.loadPage(request);
        assertNotNull(pg);
        cacher.close();
    }

    /**
     *
     * @return
     */
    private Cacher initMongoCacher(){
        String path = ResourcePathUtils.getResourceFileAbsPath(CacherTest.class, "/MongoCacherTest.yaml");
        TaskConfig taskConfig = null;
        try {
            taskConfig = new TaskConfig(path);
        } catch (IOException e) {
            fail(e.getLocalizedMessage());
        }
        Cacher cacher = new MongoCacher(taskConfig);
        return cacher;
    }

    /**
     *
     * @return
     */
    private Cacher initLocalDiskCacher()  {
        String path = ResourcePathUtils.getResourceFileAbsPath(CacherTest.class, "/CacherTest.yaml");
        TaskConfig taskConfig = null;
        try {
            taskConfig = new TaskConfig(path);
        } catch (IOException e) {
            fail(e.getLocalizedMessage());
        }
        Cacher cacher = new LocalDiskCacher(taskConfig);
        return cacher;
    }
}
