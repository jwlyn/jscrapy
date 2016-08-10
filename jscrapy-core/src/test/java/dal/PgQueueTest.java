package dal;

import org.jscrapy.core.dal.UrlQueueMapper;
import org.jscrapy.core.dal.pg.PgUrlQueueMapper;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by cxu on 2016/8/10.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PgQueueTest.class)
@SpringBootApplication(scanBasePackages = {"org.jscrapy.core.bootcfg"})
@TestPropertySource("classpath:db.properties")
public class PgQueueTest extends QueueTest {
    @Autowired
    PgUrlQueueMapper pgUrlQueueMapper;

    @Override
    protected UrlQueueMapper getQueueMapper() {
        return pgUrlQueueMapper;
    }
}
