package org.jscrapy.core.config;

/**
 * Created by cxu on 2016/7/26.
 */
public class ConfigDriver {

    private JscrapyConfig JscrapyConfig;

    public ConfigDriver(JscrapyConfig JscrapyConfig) {
        this.JscrapyConfig = JscrapyConfig;
    }

    public JscrapyConfig getJscrapyConfig() {
        return JscrapyConfig;
    }
}
