package com.oxf1.myspider.config.impl;

import com.oxf1.myspider.config.ConfigOperator;
import com.oxf1.myspider.exception.MySpiderExceptionCode;
import com.oxf1.myspider.exception.MySpiderFetalException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    final static Logger logger = LoggerFactory.getLogger(YamlConfigOperator.class);
    String persistencePath;//持久化文件的磁盘地址
    private LinkedHashMap<String, Object> configMap = new LinkedHashMap<String, Object>(20);

    public YamlConfigOperator(String cfgFilePath) throws MySpiderFetalException {
        laod(cfgFilePath);
    }

    /**
     * 重新从文件里load参数
     */
    private void laod(String cfgFilePath) throws MySpiderFetalException {
        Yaml yaml = new Yaml();
        String yamlStr = null;
        try {
            yamlStr = FileUtils.readFileToString(new File(cfgFilePath));
        } catch (IOException e) {
            logger.error("读取配置文件失败{}", e);
            MySpiderFetalException exp = new MySpiderFetalException(MySpiderExceptionCode.YAML_READ_FILE_ERROR);
            exp.setErrorMessage(e.getLocalizedMessage());
            throw exp;
        }
        configMap = (LinkedHashMap<String, Object>)yaml.loadAs(yamlStr, LinkedHashMap.class);
    }

    @Override
    public Object loadValue(String key) {
        Object obj = configMap.get(key);
        return obj;
    }

    @Override
    public void put(String key, Object value) throws MySpiderFetalException {
        configMap.put(key, value);
        onChange();
    }

    @Override
    public void setPersistencePath(String cfgSavePath) {
        this.persistencePath = cfgSavePath;
    }

    /**
     * 当配置变化的时候，持久化到磁盘一份
     * //TODO 修改为APO的方式监控
     */
    private void onChange() throws MySpiderFetalException {
        Yaml yaml = new Yaml();
        String config = yaml.dumpAsMap(configMap);
        try {
            if(StringUtils.isNotBlank(this.persistencePath)){
                FileUtils.writeStringToFile(new File(this.persistencePath), config, StandardCharsets.UTF_8.name(), false);
            }
        } catch (IOException e) {
            logger.error("写入yaml配置文件失败{}", e);
            MySpiderFetalException exp = new MySpiderFetalException(MySpiderExceptionCode.YAML_WRITE_FILE_ERROR);
            exp.setErrorMessage(e.getLocalizedMessage());
            throw exp;
        }
    }

    @Override
    public String toString() {
        return "YamlConfigOperator{" +
                "configMap=" + configMap +
                '}';
    }
}
