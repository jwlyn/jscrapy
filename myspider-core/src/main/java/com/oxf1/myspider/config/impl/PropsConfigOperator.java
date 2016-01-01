package com.oxf1.myspider.config.impl;

import com.oxf1.myspider.config.ConfigOperator;
import com.oxf1.myspider.exception.MySpiderFetalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by cxu on 2016/1/1.
 */
public class PropsConfigOperator implements ConfigOperator {

    final static Logger logger = LoggerFactory.getLogger(PropsConfigOperator.class);

    @Override
    public Object loadValue(String key) {
        return null;
    }

    @Override
    public void put(String key, Object value) throws MySpiderFetalException {

    }

    @Override
    public void reload() throws IOException, MySpiderFetalException {

    }

    @Override
    public void rebaseConfigDir(String path) throws MySpiderFetalException {

    }
}
