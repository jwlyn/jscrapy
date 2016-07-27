package org.jscrapy.core.cacher;

import org.jscrapy.core.config.ConfigDriver;
import org.jscrapy.core.config.JscrapyConfig;
import org.jscrapy.core.exception.MySpiderFetalException;
import org.jscrapy.core.exception.MySpiderRecoverableException;
import org.jscrapy.core.page.Page;
import org.jscrapy.core.request.Request;

/**
 * 从缓存中读取网页
 * Created by cxu on 2015/7/12.
 */
public abstract class Cacher extends ConfigDriver {
    public Cacher(JscrapyConfig JscrapyConfig) {
        super(JscrapyConfig);
    }

    /**
     * @param request
     * @return 命中则返回，否则null
     */
    public abstract Page loadPage(Request request) throws MySpiderRecoverableException;

    public abstract void cachePage(Page page) throws MySpiderFetalException;

}
