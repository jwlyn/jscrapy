package com.oxf1.myspider.processor;

import com.oxf1.myspider.data.ProcessResult;
import com.oxf1.myspider.page.Page;

/**
 * Created by cxu on 2014/11/21.
 */
public interface Processor {
    public ProcessResult process(Page page);
}
