package com.oxf1.myspider.downloader;

import com.oxf1.myspider.page.Page;
import com.oxf1.myspider.request.Request;

/**
 * Created by cxu on 2014/11/21.
 */
public interface Downloader {

    public Page download(Request request);
}
