package config;

import com.oxf1.spider.TaskId;
import com.oxf1.spider.config.impl.EhcacheConfigOperator;
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
    private TaskId taskid;

    @BeforeClass
    public void setup()
    {
        taskid = new TaskId("Task-Id-For-Test");
        EhcacheConfigOperator opr = EhcacheConfigOperator.instance();
        opr.put(taskid, "strKey", STR_VALUE);
        opr.put(taskid, "intKey", INTEGER_VALUE);
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
        EhcacheConfigOperator opr = EhcacheConfigOperator.instance();
        String strVal = opr.loadString(taskid, "strKey");
        int intVal = opr.loadInt(taskid, "intKey");
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
