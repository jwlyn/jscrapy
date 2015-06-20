package com.oxf1.spider.downloader;

import com.oxf1.spider.page.Page;
import com.oxf1.spider.request.Request;

/**
 * Created by cxu on 2014/11/21.
 */
public interface Downloader {

    public Page download(Request request);
}
