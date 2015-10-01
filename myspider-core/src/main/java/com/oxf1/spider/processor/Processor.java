package com.oxf1.spider.processor;

import com.oxf1.spider.data.ProcessResult;
import com.oxf1.spider.page.Page;

/**
 * Created by cxu on 2014/11/21.
 */
public interface Processor {
    public ProcessResult process(Page page);
}
