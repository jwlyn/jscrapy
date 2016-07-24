package org.jscrapy.core.processor;

import org.jscrapy.core.data.ProcessResult;
import org.jscrapy.core.page.Page;

/**
 * Created by cxu on 2014/11/21.
 */
public interface Processor {
    public ProcessResult process(Page page);
}
