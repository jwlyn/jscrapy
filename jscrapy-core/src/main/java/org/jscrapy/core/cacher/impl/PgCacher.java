package org.jscrapy.core.cacher.impl;

import org.jscrapy.core.cacher.Cacher;
import org.jscrapy.core.exception.MySpiderFetalException;
import org.jscrapy.core.page.Page;
import org.jscrapy.core.request.HttpRequest;

/**
 * Created by cxu on 2018/2/5.
 */
public class PgCacher extends Cacher {
    @Override
    public Page loadPage(HttpRequest request) {
        return null;
    }

    @Override
    public void cachePage(Page page) throws MySpiderFetalException {

    }
}
