package dal;

import org.jscrapy.core.dal.UrlQueueMapper;
import org.jscrapy.core.dal.h2.H2UrlQueueMapper;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by cxu on 2016/8/5.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = H2QueueTest.class)
@SpringBootApplication(scanBasePackages = {"org.jscrapy.core"})
@TestPropertySource("classpath:db.properties")
public class H2QueueTest extends QueueTest {

    @Autowired
    H2UrlQueueMapper h2UrlQueueMapper;

    @Override
    protected UrlQueueMapper getQueueMapper() {
        return h2UrlQueueMapper;
    }
}
