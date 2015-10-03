package com.oxf1.spider.processor.impl;

import com.oxf1.spider.TaskConfig;
import com.oxf1.spider.component.MyspiderComponent;
import com.oxf1.spider.page.Page;
import com.oxf1.spider.data.ProcessResult;
import com.oxf1.spider.processor.Processor;
import groovy.lang.GroovyObject;

/**
 * Created by cxu on 2014/11/21.
 */
public class GroovyProcessor extends MyspiderComponent implements Processor  {

    public GroovyProcessor(TaskConfig taskConfig) {
        super(taskConfig);
    }

    @Override
    public ProcessResult process(Page page) {
        GroovyObject processor = getTaskConfig().getGroovyProcessor();
        ProcessResult result = (ProcessResult)processor.invokeMethod("process", page);
        result.setRequest(page.getRequest());
        return result;
    }

    @Override
    public void close() {

    }
}
