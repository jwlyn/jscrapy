package urlconsumer;

import dedup.DeDupTest;
import org.jscrapy.core.comsumer.UrlConsumer;
import org.jscrapy.core.config.JscrapyConfig;
import org.jscrapy.core.dal.h2queue.H2UrlQueueDo;
import org.jscrapy.core.producer.UrlProducer;
import org.jscrapy.core.request.HttpRequest;
import org.jscrapy.core.request.RequestContext;
import org.jscrapy.core.request.UrlStatus;
import org.jscrapy.core.util.Yaml2BeanUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.TestPropertySource;
import util.ResourcePathUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

/**
 * Created by cxu on 2017/1/25.
 */

@TestPropertySource("classpath:db.properties")
public class UrlConsumerTest {
    @Autowired
    @Qualifier("h2UrlConsumer")
    private UrlConsumer urlConsumer;

    @Autowired
    @Qualifier("h2UrlProducer")
    private UrlProducer urlProducer;

    @Test
    public void test() {
        JscrapyConfig jscrapyConfig = getConfig();
        assertNotNull(urlProducer);
        urlProducer.setJscrapyConfig(jscrapyConfig);

        List<RequestContext> requestContexts = rendUrl();
        int insertCount = urlProducer.push(requestContexts);
        assertEquals(1, insertCount);

        UrlConsumer[] consumers = dataProvider();
        for (UrlConsumer consumer : consumers) {
            consumer.setJscrapyConfig(jscrapyConfig);
            List<RequestContext> dequeueRequestContexts = urlConsumer.poll(1);
            for (RequestContext requestContext : dequeueRequestContexts) {
                UrlStatus status = requestContext.getUrlStatus();
                assertEquals(status, UrlStatus.NEW);
                assertEquals(requestContext.getRetryTimes(), 0);
            }

            urlConsumer.delete(dequeueRequestContexts);
        }
    }

    public UrlConsumer[] dataProvider() {
        return new UrlConsumer[]{
                urlConsumer,
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
        req.setUrlStatus(UrlStatus.NEW);
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
