package com.oxf1.spider.page;

import com.oxf1.spider.request.Request;

/**
 * Created by cxu on 2014/11/21.
 */
public class Page {

    private boolean isFromCache;
    private Request request;
    private String rawText;

    public boolean isFromCache() {
        return isFromCache;
    }

    public void setIsFromCache(boolean isFromCache) {
        this.isFromCache = isFromCache;
    }

    public Page(String rawText){
        this.rawText = rawText;
    }

    public String getRawText() {
        return rawText;
    }

    public Request getRequest(){
        return this.request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }
}
