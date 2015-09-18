package pipline;

import com.oxf1.spider.TaskConfig;
import com.oxf1.spider.config.ConfigKeys;
import com.oxf1.spider.data.DataItem;
import com.oxf1.spider.pipline.Pipline;
import com.oxf1.spider.pipline.impl.LocalFilePipline;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import org.apache.commons.io.FileUtils;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

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
    public void setup(){
        this.taskConfig = new TaskConfig("task_id", "test_name");
    }

    @AfterClass
    public void tearDown() throws IOException {
        /*删除文件*/
        String dataSavePath = taskConfig.loadString(ConfigKeys.RT_LOCAL_FILE_PIPLINE_DATA_FILE);
        FileUtils.forceDelete(new File(dataSavePath));

        /*清空缓存*/
        CacheManager cacheManager = CacheManager.create();
        Cache ehCache = cacheManager.getCache(ConfigKeys.MYSPIER_CONFIG_NAME);
        ehCache.removeAll();
        cacheManager.clearAll();
        cacheManager.shutdown();
    }

    @Test
    public void testSingleThread() throws IOException, InterruptedException {
        Pipline pipline = new LocalFilePipline(this.taskConfig);
        DataItem dt = new DataItem() {
            @Override
            public List<String> getData() {
                List<String> dt = new ArrayList<String>();
                dt.add("123");
                dt.add("345");
                return dt;
            }
        };

        for(int i=0; i<100; i++){
            pipline.save(dt);
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
