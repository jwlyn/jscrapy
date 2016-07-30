package org.jscrapy.core;

import org.jscrapy.core.config.JscrapyConfig;

/**
 * Created by cxu on 2016/7/26.
 */
public class ConfigDriver {

    private org.jscrapy.core.config.JscrapyConfig JscrapyConfig;

    public ConfigDriver(JscrapyConfig JscrapyConfig) {
        this.JscrapyConfig = JscrapyConfig;
    }

    public JscrapyConfig getJscrapyConfig() {
        return JscrapyConfig;
    }
}
