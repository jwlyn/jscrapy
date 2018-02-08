package urlproducer;

import dedup.DeDupTest;
import org.jscrapy.core.config.JscrapyConfig;
import org.jscrapy.core.dal.h2queue.H2UrlQueueDo;
import org.jscrapy.core.producer.UrlProducer;
import org.jscrapy.core.request.HttpRequest;
import org.jscrapy.core.request.RequestContext;
import org.jscrapy.core.util.Yaml2BeanUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import util.ResourcePathUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

/**
 * Created by cxu on 2017/1/21.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource("classpath:db.properties")
public class UrlProducerTest {
    @Autowired
    @Qualifier("h2UrlProducer")
    private UrlProducer urlProducer;

    @Test
    public void test() {
        JscrapyConfig jscrapyConfig = getConfig();

        UrlProducer[] producers = dataProvider();
        for (UrlProducer urlProducer : producers) {
            //插入1个，看个数增加1个
            assertNotNull(urlProducer);
            urlProducer.setJscrapyConfig(jscrapyConfig);
            List<RequestContext> requestContexts = rendUrl();
            int insertCount = urlProducer.push(requestContexts);

            assertEquals(1, insertCount);
        }
    }

    private UrlProducer[] dataProvider() {
        return new UrlProducer[]{
                urlProducer,
        };
    }

    /**
     * 生成模拟数据
     * @return
     */
    private List<RequestContext> rendUrl() {
        List<RequestContext> requestContexts = new ArrayList<>();
        RequestContext req = new RequestContext(new HttpRequest("http://jscrapy.org"), new H2UrlQueueDo());
        req.setRetryTimes(1);
        requestContexts.add(req);
        return requestContexts;
    }

    private JscrapyConfig getConfig() {
        String path = ResourcePathUtils.getResourceFileAbsPath(DeDupTest.class, "/H2UrlConsumerTest.yaml");
        JscrapyConfig jscrapyConfig = null;
        try {
            jscrapyConfig = (JscrapyConfig) Yaml2BeanUtil.loadAsBean(JscrapyConfig.class, new File(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            fail("");
        }
        return jscrapyConfig;
    }

}
