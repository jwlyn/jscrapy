package org.jscrapy.core.util;

import org.springframework.core.io.Resource;
import org.yaml.snakeyaml.Yaml;

import java.io.*;

/**
 * yaml配置文件映射为java bean
 * Created by cxu on 2016/12/29.
 */
public class Yaml2BeanUtil {
    /**
     *
     * @param clazz
     * @param file
     * @return
     */
    public static Object loadAsBean(Class clazz, File file) throws FileNotFoundException {
        Yaml yaml = new Yaml();
        Object o = yaml.loadAs(new FileInputStream(file), clazz);
        return o;
    }

    /**
     *
     * @param clazz
     * @param yamlContent
     * @return
     */
    public static Object loadAsBean(Class clazz, String yamlContent) {
        Yaml yaml = new Yaml();
        Object o = yaml.loadAs(yamlContent, clazz);
        return o;
    }

    /**
     *
     * @param clazz
     * @param resource
     * @return
     */
    public static Object loadAsBean(Class clazz, Resource resource) throws IOException {
        Yaml yaml = new Yaml();
        InputStream ins = resource.getInputStream();
        Object o = yaml.loadAs(ins, clazz);
        return o;
    }
}
