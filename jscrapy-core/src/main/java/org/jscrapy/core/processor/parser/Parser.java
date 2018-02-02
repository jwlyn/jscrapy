package org.jscrapy.core.processor.parser;

import org.jscrapy.core.data.ProcessResult;
import org.jscrapy.core.page.Page;

/**
 * Created by cxu on 2017/2/7.
 */
public interface Parser {
    public ProcessResult parse(Page page);
}
