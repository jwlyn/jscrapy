package com.oxf1.myspider.cacher;

import com.oxf1.myspider.TaskConfig;
import com.oxf1.myspider.component.MyspiderComponent;
import com.oxf1.myspider.exception.MySpiderFetalException;
import com.oxf1.myspider.exception.MySpiderRecoverableException;
import com.oxf1.myspider.page.Page;
import com.oxf1.myspider.request.Request;

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
