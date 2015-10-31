package log;

import com.oxf1.myspider.common.log.MyLoggerFactory;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

import static org.testng.Assert.assertTrue;

/**
 * Created by cxu on 2015/10/31.
 */
public class MyLoggerFactoryTest {
    @Test
    public void testLog() throws IOException {
        String logPath = getLogPath();
        Logger logger = MyLoggerFactory.getModuleLogger(MyLoggerFactoryTest.class.getSimpleName(), logPath);
        int i = 1000;
        while (i-- > 0) {
            logger.info("hahah");
        }
        String file = getLogFilePath();
        File f = new File(file);
        assertTrue(f.exists());
        FileUtils.deleteQuietly(f);
    }

    private String getLogFilePath() {
        String logPath = getLogPath();
        String file = logPath + MyLoggerFactory.getTime("yyyyMMdd") + ".log";
        return file;
    }

    private String getLogPath() {
        String workDir = System.getProperty("user.home");
        String path = workDir + File.separator + ".myspider" + File.separator + "logs" + File.separator;
        return path;
    }
}
