package com.oxf1.spider.config.impl;

import com.oxf1.spider.config.ConfigOperator;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;

/**
 * 将配置文件保存在本地磁盘
 * Created by cxu on 2015/9/18.
 */
public class YamlConfigOperator  implements ConfigOperator {

    String persistencePath;//持久化文件的磁盘地址
    private LinkedHashMap<String, Object> configMap = new LinkedHashMap<String, Object>(20);

    public YamlConfigOperator(String persistencePath){
        this.persistencePath = persistencePath;
    }

    /**
     * 重新从文件里load参数
     */
    public void reload() throws IOException {
        Yaml yaml = new Yaml();
        String yamlStr = FileUtils.readFileToString(new File(this.persistencePath));
        configMap = (LinkedHashMap<String, Object>)yaml.loadAs(yamlStr, LinkedHashMap.class);
    }

    @Override
    public Object loadValue(String key) {
        Object obj = configMap.get(key);
        return obj;
    }

    @Override
    public void put(String key, Object value) {
        configMap.put(key, value);
        onChange();
    }

    /**
     * 当配置变化的时候，持久化到磁盘一份
     */
    public void onChange(){
        Yaml yaml = new Yaml();
        String config = yaml.dumpAsMap(configMap);
        try {
            if(StringUtils.isNotBlank(this.persistencePath)){
                FileUtils.writeStringToFile(new File(this.persistencePath), config, StandardCharsets.UTF_8.name(), false);
            }
        } catch (IOException e) {
            e.printStackTrace();
            //TODO
        }
    }

/*    public static void main(String[]arg) throws IOException {
        String cfg = "C:\\Users\\cxu\\.myspider\\10.15.86.67@task_name@task_id@8384@cxu-PC\\cfg.yaml";
        YamlConfigOperator opr = new YamlConfigOperator(cfg);
        opr.reload();
        System.out.println(opr);
    }*/

    @Override
    public String toString() {
        return "YamlConfigOperator{" +
                "configMap=" + configMap +
                '}';
    }
}
