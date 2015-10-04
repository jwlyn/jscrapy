package com.oxf1.spider.cacher;

import com.oxf1.spider.TaskConfig;
import com.oxf1.spider.component.MyspiderComponent;
import com.oxf1.spider.exception.MySpiderFetalException;
import com.oxf1.spider.exception.MySpiderRecoverableException;
import com.oxf1.spider.page.Page;
import com.oxf1.spider.request.Request;

/**
 * 从缓存中读取网页
 * Created by cxu on 2015/7/12.
 */
public abstract class Cacher extends MyspiderComponent{
    public Cacher(TaskConfig taskConfig) {
        super(taskConfig);
    }

    /**
     *
     * @param request
     * @return 命中则返回，否则null
     */
    public abstract Page loadPage(Request request) throws MySpiderRecoverableException;

    public abstract void cachePage(Page page) throws MySpiderFetalException;

}
