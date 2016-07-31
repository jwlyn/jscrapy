package org.jscrapy.core.processor;

import org.jscrapy.core.ConfigDriver;
import org.jscrapy.core.config.JscrapyConfig;
import org.jscrapy.core.data.ProcessResult;
import org.jscrapy.core.page.Page;

/**
 * Created by cxu on 2014/11/21.
 */
public abstract class Processor extends ConfigDriver {
    public Processor(JscrapyConfig jscrapyConfig) {
        super(jscrapyConfig);
    }

    public abstract ProcessResult process(Page page);
}
