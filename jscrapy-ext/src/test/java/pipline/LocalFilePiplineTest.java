package pipline;

import org.apache.commons.io.FileUtils;
import org.jscrapy.core.config.JscrapyConfig;
import org.jscrapy.core.data.DataItem;
import org.jscrapy.core.exception.MySpiderFetalException;
import org.jscrapy.core.exception.MySpiderRecoverableException;
import org.jscrapy.core.pipline.Pipline;
import org.jscrapy.core.util.Yaml2BeanUtil;
import org.jscrapy.ext.pipline.LocalFilePipline;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import util.ResourcePathUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cxu on 2015/6/21.
 */
public class LocalFilePiplineTest {
    private JscrapyConfig jscrapyConfig;

    @BeforeClass
    public void setup() throws IOException, MySpiderFetalException {
        String path = ResourcePathUtils.getResourceFileAbsPath(LocalFilePiplineTest.class, "/LocalFilePiplineTest.yaml");
        Resource resource = new ClassPathResource("LocalFilePiplineTest.yaml");
        jscrapyConfig = (JscrapyConfig) Yaml2BeanUtil.loadAsBean(JscrapyConfig.class, resource);
    }

    @AfterClass
    public void tearDown() throws IOException {
        /*删除文件*/
        String tempDir = jscrapyConfig.getTaskWorkDir();
        FileUtils.forceDeleteOnExit(new File(tempDir));
    }

    @Test
    public void testSingleThread() throws IOException, InterruptedException, MySpiderRecoverableException, MySpiderFetalException {
        Pipline pipline = new LocalFilePipline(this.jscrapyConfig);
        DataItem dt = new DataItem();
        dt.put("a", "123")
                .put("b", "456");
        List<DataItem> dataItems = new ArrayList<>();
        dataItems.add(dt);

        for(int i=0; i<100; i++){
            pipline.save(dataItems);
        }

        Thread.sleep(1000);

//        try {
//            String dataSavePath = JscrapyConfig.loadString(ConfigKeys.RT_EXT_RT_LOCAL_FILE_PIPLINE_DATA_FILE);
//            List<String> lines = FileUtils.readLines(new File(dataSavePath));
//            assertEquals(100, lines.size());
//        }finally {
//            pipline.close();
//        }
    }
}
