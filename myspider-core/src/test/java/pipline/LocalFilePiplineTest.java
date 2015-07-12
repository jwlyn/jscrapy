package pipline;

import com.oxf1.spider.TaskConfig;
import com.oxf1.spider.config.ConfigKeys;
import com.oxf1.spider.config.ConfigOperator;
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
    private TaskConfig taskConfig;
    private String dataSavePath = System.getProperty("user.home")+"/"+".myspider/pipline/";

    @BeforeClass
    public void setup(){
        ConfigOperator opr = new EhcacheConfigOperator();
        this.taskConfig = new TaskConfig("task-id-for-test", "testTa", opr);
        this.dataSavePath = this.dataSavePath + taskConfig.getTaskName() + File.separator + "test.txt";
        this.taskConfig.put(ConfigKeys.LOCAL_FILE_PIPLINE_DATA_SAVE_PATH, dataSavePath);
    }

    @AfterClass
    public void tearDown() throws IOException {
        /*清空缓存*/
        CacheManager cacheManager = CacheManager.create();
        Cache ehCache = cacheManager.getCache(ConfigKeys.MYSPIER_CONFIG_NAME);
        ehCache.removeAll();
        cacheManager.clearAll();
        cacheManager.shutdown();

        /*删除文件*/
        FileUtils.forceDelete(new File(this.dataSavePath));
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
            List<String> lines = FileUtils.readLines(new File(this.dataSavePath));
            assertEquals(200, lines.size());
        }finally {
            pipline.close();
        }
    }
}
