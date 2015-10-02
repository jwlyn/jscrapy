package com.oxf1.spider.config;

import java.io.IOException;

/**
 * Created by cxu on 2015/6/21.
 */
public interface ConfigOperator {
    public Object loadValue(String key);
    public void put(String key, Object value);
    public void reload() throws IOException;
}
