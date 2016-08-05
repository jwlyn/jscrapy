package dal;

import org.jscrapy.core.dal.h2.H2UrlQueueMapper;
import org.jscrapy.core.dal.h2.UrlQueueDo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;

/**
 * Created by cxu on 2016/8/5.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = H2QueueTest.class)
@SpringBootApplication(scanBasePackages = {"org.jscrapy.core.bootcfg"})
@PropertySource("db.properties")
public class H2QueueTest {

    private final static String QUEUE_NAME = "jscrapy_queu_name";
    private final static int QUEUE_SIZE = 5;

    @Autowired
    H2UrlQueueMapper h2UrlQueueMapper;

    /**
     * 建表、插入5条数据
     */
    @Before
    public void setup() {
        this.h2UrlQueueMapper.dropQueue(QUEUE_NAME);
        this.h2UrlQueueMapper.createNewQueue(QUEUE_NAME);

        List<UrlQueueDo> urls = new ArrayList<>();
        for (long i = 0; i < QUEUE_SIZE; i++) {
            UrlQueueDo dt = new UrlQueueDo();
            dt.setUrl("http://url" + i + ".com");
            dt.setRetryTimes(1);
            dt.setUrlStatus("NEW");
            urls.add(dt);
        }
        this.h2UrlQueueMapper.batchInsert(QUEUE_NAME, urls);
    }

    @Test
    public void testUpdate() {
        int itemSelected = 1;
        List<UrlQueueDo> item = this.h2UrlQueueMapper.selectUrlByStatus(QUEUE_NAME, "NEW", itemSelected);
        assertEquals(itemSelected, item.size());

        String urlToBeUpdate = "com.jscrapy.www";
        item.get(0).setUrl(urlToBeUpdate);
        this.h2UrlQueueMapper.batchUpdate(QUEUE_NAME, item);

        boolean result = false;
        item = this.h2UrlQueueMapper.selectUrlByStatus(QUEUE_NAME, "NEW", Integer.MAX_VALUE);
        for (UrlQueueDo itm : item) {
            if (urlToBeUpdate.equalsIgnoreCase(itm.getUrl())) {
                result = true;
                break;
            }
        }

        assertTrue(result);
    }

    @Test
    public void testSelect() {
        List<UrlQueueDo> all = this.h2UrlQueueMapper.selectUrlByStatus(QUEUE_NAME, "NEW", Integer.MAX_VALUE);
        assertNotEquals(0, all.size());
    }

    @After
    public void tearDown() {

        List<UrlQueueDo> all = this.h2UrlQueueMapper.selectUrlByStatus(QUEUE_NAME, "NEW", Integer.MAX_VALUE);
        assertEquals(QUEUE_SIZE, all.size());

        this.h2UrlQueueMapper.dropQueue(QUEUE_NAME);
    }
}
