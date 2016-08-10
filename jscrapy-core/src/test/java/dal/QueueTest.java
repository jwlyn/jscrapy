package dal;

import org.jscrapy.core.dal.UrlQueueDo;
import org.jscrapy.core.dal.UrlQueueMapper;
import org.jscrapy.core.dal.h2.H2UrlQueueDo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;

/**
 * Created by cxu on 2016/8/10.
 */
public abstract class QueueTest {

    private final static String QUEUE_NAME = "jscrapy_queue_name";
    private final static int QUEUE_SIZE = 5;

    /**
     * 建表、插入5条数据
     */
    @Before
    public void setup() {
        getQueueMapper().dropQueue(QUEUE_NAME);
        getQueueMapper().createNewQueue(QUEUE_NAME);

        List<UrlQueueDo> urls = new ArrayList<>();
        for (long i = 0; i < QUEUE_SIZE; i++) {
            H2UrlQueueDo dt = new H2UrlQueueDo();
            dt.setUrl("http://url" + i + ".com");
            dt.setRetryTimes(1);
            dt.setUrlStatus("NEW");
            urls.add(dt);
        }
        getQueueMapper().batchInsert(QUEUE_NAME, urls);
    }

    @Test
    public void testUpdate() {
        int itemSelected = 1;
        List<UrlQueueDo> item = getQueueMapper().selectUrlByStatus(QUEUE_NAME, "NEW", itemSelected);
        assertEquals(itemSelected, item.size());

        String urlNewValue = "com.jscrapy.www";
        item.get(0).setUrl(urlNewValue);
        getQueueMapper().batchUpdate(QUEUE_NAME, item);

        boolean result = false;
        item = getQueueMapper().selectUrlByStatus(QUEUE_NAME, "NEW", Integer.MAX_VALUE);
        for (UrlQueueDo itm : item) {
            if (urlNewValue.equalsIgnoreCase(itm.getUrl())) {
                result = true;
                break;
            }
        }

        assertTrue(result);
    }

    @Test
    public void testSelect() {
        List<UrlQueueDo> all = getQueueMapper().selectUrlByStatus(QUEUE_NAME, "NEW", Integer.MAX_VALUE);
        assertNotEquals(0, all.size());
    }

    @After
    public void tearDown() {

        List<UrlQueueDo> all = getQueueMapper().selectUrlByStatus(QUEUE_NAME, "NEW", Integer.MAX_VALUE);
        assertEquals(QUEUE_SIZE, all.size());

        getQueueMapper().dropQueue(QUEUE_NAME);
    }

    protected abstract UrlQueueMapper getQueueMapper();
}
