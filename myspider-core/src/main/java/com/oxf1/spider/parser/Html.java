package com.oxf1.spider.parser;

import com.oxf1.spider.page.Page;
import us.codecraft.webmagic.utils.UrlUtils;

/**
 * Created by cxu on 2015/6/28.
 */
public class Html  extends us.codecraft.webmagic.selector.Html{
    public Html(Page page) {
        super(UrlUtils.fixAllRelativeHrefs(page.getRawText(), page.getRequest().getUrl()));
    }
}
