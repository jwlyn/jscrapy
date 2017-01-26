package org.jscrapy.core.downloader;

import org.jscrapy.core.page.Page;
import org.jscrapy.core.request.HttpRequest;

/**
 * Created by cxu on 2014/11/21.
 */
public interface Downloader {

    public Page download(HttpRequest request);
}
