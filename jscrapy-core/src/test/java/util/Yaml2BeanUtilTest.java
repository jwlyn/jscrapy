package util;

import org.apache.commons.io.FileUtils;
import org.jscrapy.core.config.JscrapyConfig;
import org.jscrapy.core.util.Yaml2BeanUtil;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.testng.Assert.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;

/**
 * Created by cxu on 2016/12/29.
 */
public class Yaml2BeanUtilTest {

    @Test
    public void testString2Bean() throws IOException {
        String path = ResourcePathUtils.getResourceFileAbsPath(Yaml2BeanUtil.class, "/yaml2beanTest.yaml");
        String yamlString = FileUtils.readFileToString(new File(path));
        JscrapyConfig config = (JscrapyConfig) Yaml2BeanUtil.loadAsBean(JscrapyConfig.class, yamlString);
        assertNotNull(config);
        assertEquals(config.getTaskId(), "task.id");

    }

    @Test
    public void testFile2Bean() throws FileNotFoundException {
        String path = ResourcePathUtils.getResourceFileAbsPath(Yaml2BeanUtil.class, "/yaml2beanTest.yaml");
        File f = new File(path);
        JscrapyConfig config = (JscrapyConfig) Yaml2BeanUtil.loadAsBean(JscrapyConfig.class, f);
        assertNotNull(config);
        assertEquals(config.getTaskId(), "task.id");
    }

    @Test
    public void testResource2Bean() throws IOException {
        Resource resource = new ClassPathResource("yaml2beanTest.yaml");
        JscrapyConfig config = (JscrapyConfig) Yaml2BeanUtil.loadAsBean(JscrapyConfig.class, resource);
        assertNotNull(config);
        assertEquals(config.getTaskId(), "task.id");
    }


}
