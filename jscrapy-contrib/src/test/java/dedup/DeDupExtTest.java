package dedup;

import org.jscrapy.contrib.dedup.EhCacheDedup;
import org.jscrapy.core.TaskConfig;
import org.jscrapy.core.dedup.DeDup;
import org.jscrapy.core.exception.MySpiderFetalException;
import org.jscrapy.core.exception.MySpiderRecoverableException;
import org.jscrapy.core.request.HttpRequestMethod;
import org.jscrapy.core.request.Request;
import org.jscrapy.core.request.impl.HttpRequest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.ResourcePathUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;

/**
 * Created by cxu on 2015/12/19.
 */
public class DeDupExtTest{
    private Request rq = new HttpRequest("http://url1", HttpRequestMethod.DELETE, null);

    @DataProvider(name="dp")
    public DeDup[][] dataProvider() throws IOException, MySpiderFetalException {
        return new DeDup[][]{
                {initEhcacheDedup()} ,
        };
    }


    @Test(dataProvider = "dp")
    public void test(DeDup dedup) throws MySpiderRecoverableException {

        List<Request> req1 = new ArrayList<Request>();
        req1.add(rq);
        req1 = dedup.deDup(req1);
        assertEquals(1, req1.size());

        //先测试写入原来一样的，返回非空
        Request rq1 = rq;
        Request rq2 = new HttpRequest("http://url2", HttpRequestMethod.DELETE, null);
        Request rq3 = new HttpRequest("http://url3", HttpRequestMethod.DELETE, null);

        List<Request> req = new ArrayList<Request>();
        req.add(rq1);
        req.add(rq2);
        req.add(rq3);

        req = dedup.deDup(req);
        assertEquals(2, req.size());
        assertEquals(0, dedup.deDup(req).size());

        dedup.close();//清空现场，否则下一次运行会出错
    }

    /**
     *
     * @return
     */
    private DeDup initEhcacheDedup() throws IOException, MySpiderFetalException {
        String path = ResourcePathUtils.getResourceFileAbsPath(DeDupExtTest.class, "/EhCacheDedupTest.yaml");
        TaskConfig taskConfig = new TaskConfig(path);
        DeDup dp = new EhCacheDedup(taskConfig);
        return dp;
    }

}
