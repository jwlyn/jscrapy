package dedup;

import org.jscrapy.ext.dedup.RedisDedup;
import org.jscrapy.ext.modulecfg.RedisDedupConfig;
import org.jscrapy.core.config.ComponentName;
import org.jscrapy.core.config.JscrapyConfig;
import org.jscrapy.core.dedup.DeDup;
import org.jscrapy.core.exception.MySpiderFetalException;
import org.jscrapy.core.exception.MySpiderRecoverableException;
import org.jscrapy.core.request.HttpRequestMethod;
import org.jscrapy.core.request.HttpRequest;
import org.jscrapy.core.util.Yaml2BeanUtil;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import util.ResourcePathUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;

/**
 * Created by cxu on 2015/12/19.
 */
public class DeDupExtTest {
    private HttpRequest rq = new HttpRequest("http://url1", HttpRequestMethod.DELETE, null);

    @DataProvider(name = "dp")
    public DeDup[][] dataProvider() throws IOException, MySpiderFetalException {
        return new DeDup[][]{
                {initRedisDedup()},
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

        teardown(dedup);
    }

    /**
     * @return
     */
    private DeDup initRedisDedup() throws IOException, MySpiderFetalException {
        String path = ResourcePathUtils.getResourceFileAbsPath(DeDupExtTest.class, "/RedisDedupTest.yaml");
        JscrapyConfig jscrapyConfig = (JscrapyConfig) Yaml2BeanUtil.loadAsBean(JscrapyConfig.class, new File(path));
        DeDup dp = new RedisDedup();
        dp.setJscrapyConfig(jscrapyConfig);
        return dp;
    }

    /**
     *
     */
    public void teardown(DeDup dedup) {
        String dedepSetName = "jscrapy_dedup_set_" + dedup.getJscrapyConfig().getTaskFp();

        RedisDedupConfig dedupConfig = (RedisDedupConfig)dedup.getJscrapyConfig().get(ComponentName.DEDUP_REDIS);
        String redisHost = dedupConfig.getHost();
        JedisPool pool = new JedisPool(new JedisPoolConfig(), redisHost);
        Jedis jedis = pool.getResource();
        jedis.del(dedepSetName);
    }
}
