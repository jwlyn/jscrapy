package org.jscrapy.core.cacher;

import org.jscrapy.core.TaskConfig;
import org.jscrapy.core.component.MyspiderComponent;
import org.jscrapy.core.exception.MySpiderFetalException;
import org.jscrapy.core.exception.MySpiderRecoverableException;
import org.jscrapy.core.page.Page;
import org.jscrapy.core.request.Request;

/**
 * 从缓存中读取网页
 * Created by cxu on 2015/7/12.
 */
public abstract class Cacher extends MyspiderComponent {
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
