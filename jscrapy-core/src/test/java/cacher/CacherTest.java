package cacher;

import org.jscrapy.common.log.MyLoggerFactory;
import org.jscrapy.core.config.JscrapyConfig;
import org.jscrapy.core.cacher.Cacher;
import org.jscrapy.core.cacher.impl.LocalDiskCacher;
import org.jscrapy.core.cacher.impl.MongoCacher;
import org.jscrapy.core.exception.MySpiderFetalException;
import org.jscrapy.core.exception.MySpiderRecoverableException;
import org.jscrapy.core.page.Page;
import org.jscrapy.core.request.HttpRequestMethod;
import org.jscrapy.core.request.Request;
import org.jscrapy.core.request.impl.HttpRequest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.ResourcePathUtils;

import java.io.IOException;

import static org.testng.Assert.assertNotNull;

/**
 * Created by cxu on 2015/9/19.
 */
public class CacherTest {
    private Page page;
    private Request request;

    @BeforeClass
    public void setup() {
        request = new HttpRequest("http://oxf1.com/test", HttpRequestMethod.DELETE, null);
        page = new Page("this is html content, hahaha!");
        page.setRequest(request);
    }

    @DataProvider(name = "dp")
    public Cacher[][] dataProvider() throws IOException, MySpiderFetalException {
        return new Cacher[][]{
                {initLocalDiskCacher()},
                {initMongoCacher()}
        };
    }

    @Test(dataProvider = "dp")
    public void test(Cacher cacher) throws  MySpiderFetalException {
        try {
            cacher.cachePage(page);
            Page pg = cacher.loadPage(request);
            assertNotNull(pg);
        } catch (MySpiderRecoverableException e) {

        }
    }

    /**
     * @return
     */
    private Cacher initMongoCacher() throws IOException, MySpiderFetalException {
        String path = ResourcePathUtils.getResourceFileAbsPath(CacherTest.class, "/MongoCacherTest.yaml");
        JscrapyConfig JscrapyConfig = null;
        JscrapyConfig = new JscrapyConfig();
        Cacher cacher = new MongoCacher(JscrapyConfig);
        return cacher;
    }

    /**
     * @return
     */
    private Cacher initLocalDiskCacher() throws IOException, MySpiderFetalException {
        String path = ResourcePathUtils.getResourceFileAbsPath(CacherTest.class, "/CacherTest.yaml");
        JscrapyConfig JscrapyConfig = null;
        JscrapyConfig = new JscrapyConfig();
        Cacher cacher = new LocalDiskCacher(JscrapyConfig);
        return cacher;
    }
}
