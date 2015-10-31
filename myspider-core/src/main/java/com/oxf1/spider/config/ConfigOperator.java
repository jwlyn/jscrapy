package com.oxf1.spider.config;

import com.oxf1.spider.exception.MySpiderFetalException;

import java.io.IOException;

/**
 * Created by cxu on 2015/6/21.
 */
public interface ConfigOperator {
    public Object loadValue(String key);
    public void put(String key, Object value) throws MySpiderFetalException;
    public void reload() throws IOException, MySpiderFetalException;

    public void rebaseConfigDir(String path) throws MySpiderFetalException;
}
