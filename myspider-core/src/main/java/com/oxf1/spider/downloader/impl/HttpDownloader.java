package com.oxf1.spider.downloader.impl;

import com.oxf1.spider.TaskConfig;
import com.oxf1.spider.component.MyspiderComponent;
import com.oxf1.spider.downloader.Downloader;
import com.oxf1.spider.page.Page;
import com.oxf1.spider.request.Request;

/**
 * Created by cxu on 2014/11/21.
 */
public class HttpDownloader extends MyspiderComponent implements Downloader{

    public HttpDownloader(TaskConfig taskConfig) {
        super(taskConfig);
    }

    @Override
    public Page download(Request request) {
        return null;
    }

    @Override
    public void close() {

    }
}
