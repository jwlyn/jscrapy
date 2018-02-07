package org.jscrapy.core.status;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by cxu on 2015/9/30.
 */
public class TaskStatus {
    public enum Status{
        RUN, PAUSE, CANCEL
    }

    private AtomicLong cacheHitUrlCnt = new AtomicLong(0); //缓存命中的个数
    private AtomicLong netUrlCnt = new AtomicLong(0);//网络抓取的个数
    private AtomicLong failedUrlCnt = new AtomicLong(0);//失败抓取的次数
    private AtomicLong pageSizeKb = new AtomicLong(0);//页面大小
    private AtomicLong dataItemCnt = new AtomicLong(0);//解析出的数据条数

    public AtomicLong getCacheHitUrlCnt() {
        return cacheHitUrlCnt;
    }

    public void setCacheHitUrlCnt(AtomicLong cacheHitUrlCnt) {
        this.cacheHitUrlCnt = cacheHitUrlCnt;
    }

    public AtomicLong getNetUrlCnt() {
        return netUrlCnt;
    }

    public void setNetUrlCnt(AtomicLong netUrlCnt) {
        this.netUrlCnt = netUrlCnt;
    }

    public AtomicLong getFailedUrlCnt() {
        return failedUrlCnt;
    }

    public void setFailedUrlCnt(AtomicLong failedUrlCnt) {
        this.failedUrlCnt = failedUrlCnt;
    }

    public AtomicLong getPageSizeKb() {
        return pageSizeKb;
    }

    public void setPageSizeKb(AtomicLong pageSizeKb) {
        this.pageSizeKb = pageSizeKb;
    }

    public AtomicLong getDataItemCnt() {
        return dataItemCnt;
    }

    public void setDataItemCnt(AtomicLong dataItemCnt) {
        this.dataItemCnt = dataItemCnt;
    }

    @Override
    public String toString() {
        return "TaskStatus{" +
                "cacheHitUrlCnt=" + cacheHitUrlCnt +
                ", netUrlCnt=" + netUrlCnt +
                ", failedUrlCnt=" + failedUrlCnt +
                ", pageSizeKb=" + pageSizeKb +
                ", dataItemCnt=" + dataItemCnt +
                '}';
    }
}
