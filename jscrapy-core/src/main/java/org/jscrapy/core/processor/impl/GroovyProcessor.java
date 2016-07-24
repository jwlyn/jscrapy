package org.jscrapy.core.processor.impl;

import org.jscrapy.core.TaskConfig;
import org.jscrapy.core.component.MyspiderComponent;
import org.jscrapy.core.data.ProcessResult;
import org.jscrapy.core.page.Page;
import org.jscrapy.core.processor.Processor;
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
        GroovyObject processor = getTaskConfig().getGroovyProcessorObject();
        ProcessResult result = (ProcessResult)processor.invokeMethod("process", page);
        result.setRequest(page.getRequest());
        return result;
    }

    @Override
    public void close() {

    }
}
