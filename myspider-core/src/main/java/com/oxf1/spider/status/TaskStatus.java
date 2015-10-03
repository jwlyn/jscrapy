package com.oxf1.spider.status;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by cxu on 2015/9/30.
 */
public class TaskStatus {
    public enum Status{
        RUN, PAUSE, CANCEL
    }

    private AtomicLong cacheUrl = new AtomicLong(0);
    private AtomicLong netUrl = new AtomicLong(0);
    private AtomicLong failedUrl = new AtomicLong(0);
    private AtomicLong pageSizeKb = new AtomicLong(0);
    private AtomicLong dataItemCount = new AtomicLong(0);

    public AtomicLong getCacheUrl() {
        return cacheUrl;
    }

    public void addCacheUrl(long n) {
        cacheUrl.addAndGet(n);
    }

    public Long getNetUrl() {
        return netUrl.longValue();
    }

    public void addNetUrl(long n) {
        netUrl.addAndGet(n);
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

    public Long getDataItemCount() {
        return dataItemCount.longValue();
    }

    public void addDataItemCount(long n) {
        this.dataItemCount.addAndGet(n);
    }

    @Override
    public String toString() {
        return "TaskStatus{" +
                "cacheUrl=" + cacheUrl +
                ", netUrl=" + netUrl +
                ", failedUrl=" + failedUrl +
                ", pageSizeKb=" + pageSizeKb +
                ", dataItemCount=" + dataItemCount +
                '}';
    }
}
