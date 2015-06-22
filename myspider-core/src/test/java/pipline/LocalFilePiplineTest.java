package pipline;

import com.oxf1.spider.TaskId;
import com.oxf1.spider.config.ConfigKeys;
import com.oxf1.spider.config.impl.EhcacheConfigOperator;
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
    private TaskId taskid;
    private String dataSavePath = System.getProperty("user.home")+"/"+".myspider/localfilepiplinetest/test.txt";

    @BeforeClass
    public void setup(){
        taskid = new TaskId("task-id-for-test", "testTa");
        EhcacheConfigOperator opr = EhcacheConfigOperator.instance();
        opr.put(taskid, ConfigKeys.LOCAL_FILE_PIPLINE_DATA_SAVE_PATH, dataSavePath);
    }

    @AfterClass
    public void tearDown() throws IOException {
        /*清空缓存*/
        CacheManager cacheManager = CacheManager.create();
        Cache ehCache = cacheManager.getCache(ConfigKeys.EH_CACHE_NAME);
        ehCache.removeAll();
        cacheManager.clearAll();
        cacheManager.shutdown();

        /*删除文件*/
        FileUtils.forceDelete(new File(dataSavePath));
    }

    @Test
    public void testSingleThread() throws IOException, InterruptedException {
        Pipline pipline = new LocalFilePipline(taskid);
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
            List<String> lines = FileUtils.readLines(new File(dataSavePath));
            assertEquals(200, lines.size());
        }finally {
            pipline.close();
        }
    }
}
