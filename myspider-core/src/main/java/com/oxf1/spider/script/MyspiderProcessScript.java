package com.oxf1.spider.script;

import com.oxf1.spider.page.Page;
import com.oxf1.spider.data.ProcessResult;

/**
 * 脚本扩展接口
 * Created by cxu on 2015/10/1.
 */
public interface MyspiderProcessScript {
    public ProcessResult process(Page page);
}
