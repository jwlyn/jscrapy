package org.jscrapy.core.processor.impl;

import groovy.lang.GroovyObject;
import org.jscrapy.core.config.JscrapyConfig;
import org.jscrapy.core.data.ProcessResult;
import org.jscrapy.core.page.Page;
import org.jscrapy.core.processor.Processor;
import org.jscrapy.core.processor.ruletable.ParseRuleTable;

/**
 * Created by cxu on 2014/11/21.
 */
public class GroovyProcessor extends Processor {
    private ParseRuleTable ruleTable;

    public GroovyProcessor(JscrapyConfig jscrapyConfig) {
        super(jscrapyConfig);
    }

    @Override
    public ProcessResult process(Page page) {
        GroovyObject processor = ruleTable.getProcessorRule();
        ProcessResult result = (ProcessResult) processor.invokeMethod("process", page);
        result.setRequest(page.getRequest());
        return result;
    }
}
