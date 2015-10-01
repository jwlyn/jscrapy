package com.oxf1.spider.status;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by cxu on 2015/9/30.
 */
public class TaskStatus {
    public enum Status{
        RUN, STOP, CANCEL
    }


    private AtomicLong succUrl = new AtomicLong(0);
    private AtomicLong failedUrl = new AtomicLong(0);
    private AtomicLong pageSizeKb = new AtomicLong(0);
    private AtomicLong dataSizeKb = new AtomicLong(0);

    public Long getSuccUrl() {
        return succUrl.longValue();
    }

    public void addSuccUrl(long n) {
        succUrl.addAndGet(n);
    }

    public Long getFailedUrl() {
        return failedUrl.longValue();
    }

    public void addFailedUrl(long n) {
        this.failedUrl.addAndGet(n);
    }

    public Long getPageSizeKb() {
        return pageSizeKb.longValue();
    }

    public void addPageSizeKb(long n) {
        this.pageSizeKb.addAndGet(n);
    }

    public Long getDataSizeKb() {
        return dataSizeKb.longValue();
    }

    public void addDataSizeKb(long n) {
        this.dataSizeKb.addAndGet(n);
    }
}
