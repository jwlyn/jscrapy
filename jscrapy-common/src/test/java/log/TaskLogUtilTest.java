package log;

import org.jscrapy.common.log.MyLoggerFactory;
import org.jscrapy.common.log.TaskLogUtil;
import org.slf4j.Logger;
import org.testng.annotations.Test;

/**
 * Created by cxu on 2015/11/7.
 */
public class TaskLogUtilTest {
    @Test
    public void test() {
        Logger lg = MyLoggerFactory.getLogger(TaskLogUtilTest.class);
        TaskLogUtil.log(lg, "error", "hello {}, my name {}", "world", "cxu");
        TaskLogUtil.log(lg, "info", "hello {}, my name {}", "world", "cxu");
        TaskLogUtil.log(lg, "debug", "hello {}, my name {}", "world", "cxu");
        TaskLogUtil.log(lg, "warn", "hello {}, my name {}", "world", "cxu");
    }
}
