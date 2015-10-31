package pipline;

import com.oxf1.spider.TaskConfig;
import com.oxf1.spider.config.ConfigKeys;
import com.oxf1.spider.data.DataItem;
import com.oxf1.spider.exception.MySpiderFetalException;
import com.oxf1.spider.exception.MySpiderRecoverableException;
import com.oxf1.spider.pipline.Pipline;
import com.oxf1.spider.pipline.impl.LocalFilePipline;
import org.apache.commons.io.FileUtils;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import util.ResourcePathUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;

/**
 * Created by cxu on 2015/6/21.
 */
public class LocalFilePiplineTest {
    private TaskConfig taskConfig;

    @BeforeClass
    public void setup() throws IOException, MySpiderFetalException {
        String path = ResourcePathUtils.getResourceFileAbsPath(LocalFilePiplineTest.class, "/LocalFilePiplineTest.yaml");
        this.taskConfig = new TaskConfig(path);
    }

    @AfterClass
    public void tearDown() throws IOException {
        /*删除文件*/
        String tempDir = taskConfig.getTaskWorkDir() + taskConfig.getTaskFp();
        FileUtils.deleteDirectory(new File(tempDir));
    }

    @Test
    public void testSingleThread() throws IOException, InterruptedException, MySpiderRecoverableException, MySpiderFetalException {
        Pipline pipline = new LocalFilePipline(this.taskConfig);
        DataItem dt = new DataItem();
        dt.put("a", "123")
                .put("b", "456");
        List<DataItem> dataItems = new ArrayList<>();
        dataItems.add(dt);

        for(int i=0; i<100; i++){
            pipline.save(dataItems);
        }

        Thread.sleep(1000);

        try {
            String dataSavePath = taskConfig.loadString(ConfigKeys.RT_LOCAL_FILE_PIPLINE_DATA_FILE);
            List<String> lines = FileUtils.readLines(new File(dataSavePath));
            assertEquals(100, lines.size());
        }finally {
            pipline.close();
        }
    }
}
