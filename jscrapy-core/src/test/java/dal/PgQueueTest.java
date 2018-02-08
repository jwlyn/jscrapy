package dal;

import org.jscrapy.core.dal.UrlQueueMapper;
import org.jscrapy.core.dal.pgqueue.PgUrlQueueMapper;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by cxu on 2016/8/10.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource("classpath:db.properties")
public class PgQueueTest extends QueueTest {
    @Autowired
    PgUrlQueueMapper pgUrlQueueMapper;

    @Override
    protected UrlQueueMapper getQueueMapper() {
        return pgUrlQueueMapper;
    }
}
