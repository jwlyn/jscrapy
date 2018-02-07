package org.jscrapy.core;

import org.jscrapy.core.config.JscrapyConfig;

/**
 * Created by cxu on 2018/2/5.
 */
public class JscrapyComponent implements ConfigAble {

    private JscrapyConfig JscrapyConfig;

    @Override
    public JscrapyConfig getJscrapyConfig() {
        return JscrapyConfig;
    }

    @Override
    public void setJscrapyConfig(JscrapyConfig jscrapyConfig) {
        JscrapyConfig = jscrapyConfig;
    }
}
