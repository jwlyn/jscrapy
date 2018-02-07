package org.jscrapy.core.cacher;

import org.jscrapy.core.JscrapyComponent;
import org.jscrapy.core.exception.MySpiderFetalException;
import org.jscrapy.core.page.Page;
import org.jscrapy.core.request.HttpRequest;

/**
 * 从缓存中读取网页
 * Created by cxu on 2015/7/12.
 */
public abstract class Cacher extends JscrapyComponent {
    public Cacher() {

    }

    /**
     * @param request
     * @return 命中则返回，否则null
     */
    public abstract Page loadPage(HttpRequest request);

    public abstract void cachePage(Page page) throws MySpiderFetalException;

}
