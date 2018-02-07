package org.jscrapy.core;

import org.jscrapy.core.config.JscrapyConfig;

/**
 * Created by cxu on 2016/7/26.
 */
public interface ConfigAble {

     public  abstract JscrapyConfig getJscrapyConfig();

      public abstract void setJscrapyConfig(JscrapyConfig jscrapyConfig);
}
