package org.jscrapy.core.downloader;

import org.jscrapy.core.page.Page;
import org.jscrapy.core.request.Request;

/**
 * Created by cxu on 2014/11/21.
 */
public interface Downloader {

    public Page download(Request request);
}
