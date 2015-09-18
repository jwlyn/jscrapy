package config;

import com.oxf1.spider.TaskConfig;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Created by cxu on 2015/6/21.
 */
public class ConfigTest {
    private static String EH_CACHE_NAME = "com.oxf1.myspider.config.task";
    private static String STR_VALUE = "hello world";
    private static int INTEGER_VALUE = 1;
    private TaskConfig taskConfig;

    @BeforeClass
    public void setup()
    {
        taskConfig = new TaskConfig("Task-Id-For-Test", "testTask");

        taskConfig.put("strKey", STR_VALUE);
        taskConfig.put("intKey", INTEGER_VALUE);
    }

    @AfterClass
    public void tearDown()
    {
        CacheManager cacheManager = CacheManager.create();
        Cache ehCache = cacheManager.getCache(EH_CACHE_NAME);
        ehCache.removeAll();
        cacheManager.clearAll();
        cacheManager.shutdown();
    }

    @Test
    public void testRead()
    {
        String strVal = this.taskConfig.loadString(taskConfig, "strKey");
        int intVal = this.taskConfig.loadInt(taskConfig, "intKey");
        assertEquals(STR_VALUE, strVal);
        assertEquals(INTEGER_VALUE, intVal);

    }

    private Cache getCache()
    {
        CacheManager cacheManager;
        Cache ehCache;
        cacheManager = CacheManager.create();
        ehCache = cacheManager.getCache(EH_CACHE_NAME);
        return ehCache;
    }
}
