package com.oxf1.myspider.page;

import com.oxf1.myspider.request.Request;
import org.apache.commons.lang3.StringUtils;

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

    public Page() {

    }

    public Page(String rawText){
        this.rawText = rawText;
    }

    public String getRawText() {
        return rawText;
    }

    public void setRawText(String rawText) {
        this.rawText = rawText;
    }

    public Request getRequest(){
        return this.request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public long sizeInKb() {
        if (StringUtils.isBlank(rawText)) {
            return 0;
        }
        else{
            int len = rawText.length();
            return Math.round(len / 1024);
        }
    }
}
