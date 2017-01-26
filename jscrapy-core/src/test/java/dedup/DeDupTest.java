package dedup;

import org.jscrapy.core.config.JscrapyConfig;
import org.jscrapy.core.dedup.DeDup;
import org.jscrapy.core.dedup.impl.MongoDedup;
import org.jscrapy.core.exception.MySpiderFetalException;
import org.jscrapy.core.exception.MySpiderRecoverableException;
import org.jscrapy.core.request.HttpRequest;
import org.jscrapy.core.request.HttpRequestMethod;
import org.jscrapy.core.util.Yaml2BeanUtil;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.ResourcePathUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

/**
 * Created by cxu on 2015/9/19.
 */
public class DeDupTest {
    private HttpRequest rq = new HttpRequest("http://url1", HttpRequestMethod.DELETE, null);

    @DataProvider(name = "dp")
    public Object[][] dataProvider() throws IOException, MySpiderFetalException {
        return new DeDup[][]{
                {initMongoDedup()},
        };
    }


    @Test(dataProvider = "dp")
    public void test(DeDup dedup) throws MySpiderRecoverableException {

        List<HttpRequest> req1 = new ArrayList<>();
        req1.add(rq);
        req1 = dedup.deDup(req1);
        assertEquals(1, req1.size());

        //先测试写入原来一样的，返回非空
        HttpRequest rq1 = rq;
        HttpRequest rq2 = new HttpRequest("http://url2", HttpRequestMethod.DELETE, null);
        HttpRequest rq3 = new HttpRequest("http://url3", HttpRequestMethod.DELETE, null);

        List<HttpRequest> req = new ArrayList<>();
        req.add(rq1);
        req.add(rq2);
        req.add(rq3);

        req = dedup.deDup(req);
        assertEquals(2, req.size());
        assertEquals(0, dedup.deDup(req).size());

    }

    /**
     * @return
     */
    private DeDup initMongoDedup(){

        String path = ResourcePathUtils.getResourceFileAbsPath(DeDupTest.class, "/MongoDedupTest.yaml");
        JscrapyConfig jscrapyConfig = null;
        try {
            jscrapyConfig = (JscrapyConfig) Yaml2BeanUtil.loadAsBean(JscrapyConfig.class, new File(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            fail("");
        }
        DeDup dp = new MongoDedup(jscrapyConfig);
        return dp;
    }
}
