package pipline;

import org.jscrapy.core.config.JscrapyConfig;
import org.jscrapy.core.config.ConfigKeys;
import org.jscrapy.core.data.DataItem;
import org.jscrapy.core.exception.MySpiderFetalException;
import org.jscrapy.core.exception.MySpiderRecoverableException;
import org.jscrapy.core.pipline.Pipline;
import org.jscrapy.core.pipline.impl.LocalFilePipline;
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
    private JscrapyConfig JscrapyConfig;

    @BeforeClass
    public void setup() throws IOException, MySpiderFetalException {
        String path = ResourcePathUtils.getResourceFileAbsPath(LocalFilePiplineTest.class, "/LocalFilePiplineTest.yaml");
        this.JscrapyConfig = new JscrapyConfig(path);
    }

    @AfterClass
    public void tearDown() throws IOException {
        /*删除文件*/
        String tempDir = JscrapyConfig.getSpiderWorkDir() + JscrapyConfig.getTaskFp();
        FileUtils.forceDeleteOnExit(new File(tempDir));
    }

    @Test
    public void testSingleThread() throws IOException, InterruptedException, MySpiderRecoverableException, MySpiderFetalException {
        Pipline pipline = new LocalFilePipline(this.JscrapyConfig);
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
            String dataSavePath = JscrapyConfig.loadString(ConfigKeys.RT_EXT_RT_LOCAL_FILE_PIPLINE_DATA_FILE);
            List<String> lines = FileUtils.readLines(new File(dataSavePath));
            assertEquals(100, lines.size());
        }finally {
            pipline.close();
        }
    }
}
