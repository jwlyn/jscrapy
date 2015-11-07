package downloader;

import com.oxf1.spider.TaskConfig;
import com.oxf1.spider.downloader.Downloader;
import com.oxf1.spider.downloader.impl.HttpDownloader;
import com.oxf1.spider.exception.MySpiderFetalException;
import com.oxf1.spider.page.Page;
import com.oxf1.spider.parser.Html;
import com.oxf1.spider.request.Request;
import com.oxf1.spider.request.impl.HttpRequest;
import org.testng.annotations.Test;
import util.ResourcePathUtils;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * Created by cxu on 2015/11/7.
 */
public class HttpDownloaderTest {

    @Test
    public void testDownloadCanWork() throws MySpiderFetalException {
        String path = ResourcePathUtils.getResourceFileAbsPath(HttpDownloaderTest.class, "/CacherTest.yaml");
        TaskConfig taskConfig = null;
        taskConfig = new TaskConfig(path);

        String url = "http://www.oschina.net/";
        Request request = new HttpRequest(url);
        Downloader dl = new HttpDownloader(taskConfig);
        Page pg = dl.download(request);
        assertNotNull(pg);
        Html html = new Html(pg);
        String title = html.$("title").xpath("//title/text()").get();
        assertEquals("开源中国 - 找到您想要的开源项目，分享和交流", title);
    }
}
