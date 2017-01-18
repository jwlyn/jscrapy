package org.jscrapy.core;

import org.jscrapy.core.config.JscrapyConfig;

/**
 * Created by cxu on 2016/7/26.
 */
public class ConfigDriver {

    private JscrapyConfig JscrapyConfig;

    public ConfigDriver() {

    }

    public JscrapyConfig getJscrapyConfig() {
        return JscrapyConfig;
    }

    public void setJscrapyConfig(JscrapyConfig jscrapyConfig) {
        JscrapyConfig = jscrapyConfig;
    }
}
